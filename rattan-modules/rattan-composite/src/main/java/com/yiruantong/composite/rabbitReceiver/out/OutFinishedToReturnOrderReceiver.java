package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InReturnActionEnum;
import com.yiruantong.common.core.enums.in.InReturnEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.service.InReturn;
import com.yiruantong.inbound.domain.service.InReturnDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.service.IInReturnDetailService;
import com.yiruantong.inbound.service.service.IInReturnService;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出库完成后更新到货退货单
 */
@RequiredArgsConstructor
@Service
public class OutFinishedToReturnOrderReceiver implements IRabbitReceiver {
  private final IInReturnService inReturnService;
  private final IInReturnDetailService inReturnDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IInOrderDetailService inOrderDetailService;

  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;
  private final IInReturnStatusHistoryService inReturnStatusHistoryService;

  private final ITaskQueueService taskQueueService;

  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_FINISHED_TO_RETURN_ORDER); // 接收哪几种类型的数据
  }

  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }
    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);
    try {
      InReturn inReturn = inReturnService.getById(rabbitReceiverDto.getSourceId());
      if (ObjectUtil.isNull(inReturn)) {
        transactionManager.commit(transaction); // 手动回滚事务
        R.ok("未找到对应的出库单！");
      }
      if (!StrUtil.equals(inReturn.getReturnStatus(), InReturnEnum.TO_OUT_ORDER.getName())) {
        transactionManager.commit(transaction); // 手动提交事务
        return R.ok();
      }
      List<OutOrderDetail> orderDetail = outOrderDetailService.selectListByMainId(rabbitReceiverDto.getBillId());

      //回更退货单出库数量
      for (OutOrderDetail detail : orderDetail) {
        LambdaUpdateWrapper<InReturnDetail> detialWarpper = new LambdaUpdateWrapper<>();
        detialWarpper.set(InReturnDetail::getOutQuantity, detail.getQuantityOuted())
          .eq(InReturnDetail::getReturnDetailId, detail.getSourceDetailId())
          .eq(InReturnDetail::getReturnId, detail.getSourceMainId());
        inReturnDetailService.update(detialWarpper);
      }
      if (StrUtil.isNotEmpty(inReturn.getOrderCode())) {
        //回更预到货单
        List<InReturnDetail> details = inReturnDetailService.selectListByMainId(inReturn.getReturnId());
        for (InReturnDetail detailInfo : details) {
          LambdaUpdateWrapper<InOrderDetail> detialWarpper = new LambdaUpdateWrapper<>();
          detialWarpper.setSql("return_quantity =IFNULL(return_quantity,0) + " + detailInfo.getOutQuantity())
            .eq(InOrderDetail::getOrderDetailId, detailInfo.getSourceDetailId())
            .eq(InOrderDetail::getOrderId, detailInfo.getSourceMainId());
          inOrderDetailService.update(detialWarpper);
        }
      }

      // 已出货数量
      BigDecimal outQuantityOuted = orderDetail.stream().filter(item -> B.isGreater(item.getQuantityOuted())).map(OutOrderDetail::getQuantityOuted).reduce(BigDecimal.ZERO, BigDecimal::add);
      // 已出货数量
      BigDecimal outQuantityOrder = orderDetail.stream().filter(item -> B.isGreater(item.getQuantityOrder())).map(OutOrderDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);

      InReturnEnum inReturnEnum = InReturnEnum.OUT_FINISHED;
      //计算是否部分出库
      if (B.isGreater(outQuantityOuted) && B.isLess(outQuantityOuted, outQuantityOrder)) {
        inReturnEnum = InReturnEnum.PARTIAL_TO_OUT;
      }
      //退货单添加轨迹
      inReturnStatusHistoryService.addHistoryInfo(inReturn, InReturnActionEnum.OUT_ORDER, InReturnEnum.TO_OUT_ORDER, inReturnEnum, "");


      LambdaUpdateWrapper<InReturn> returnWarpper = new LambdaUpdateWrapper<>();
      returnWarpper.set(InReturn::getReturnStatus, inReturnEnum.getName())
        .eq(InReturn::getReturnId, rabbitReceiverDto.getSourceId());
      inReturnService.update(returnWarpper);

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
    }
    return R.ok();
  }
}

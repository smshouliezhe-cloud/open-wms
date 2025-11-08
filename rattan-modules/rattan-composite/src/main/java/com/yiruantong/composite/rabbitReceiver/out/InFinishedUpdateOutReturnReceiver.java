package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.out.OutReturnStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.service.OutReturn;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import com.yiruantong.outbound.service.service.IOutReturnService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InFinishedUpdateOutReturnReceiver implements IRabbitReceiver {

  private final IOutReturnService outReturnService;
  private final IInOrderService inOrderService;
  private final IOutReturnDetailService outReturnDetailService;
  private final IInOrderDetailService inOrderDetailService;
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderDetailService outOrderDetailService;


  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_FINISHED_UPDATE_OUT_RETURN); // 接收哪几种类型的数据
  }


  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }
    // 手动开启事务  start
    DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    TransactionStatus transaction = transactionManager.getTransaction(definition);
    try {
      InOrder inOrderInfo = inOrderService.getById(rabbitReceiverDto.getBillId());


      // 如果入库单状态为出库退货转入  操作完入库后的状态要更新到出库退货单
      if (B.isEqual(inOrderInfo.getSourceType(), InOrderTypeEnum.RETURN_TO_ORDER.getName())) {

        LambdaUpdateWrapper<OutReturn> lambda1 = new UpdateWrapper<OutReturn>().lambda();
        if (B.isEqual(inOrderInfo.getOrderStatus(), InOrderStatusEnum.FINISHED.getName())) {
          // 修改入库计划单状态
          lambda1.set(OutReturn::getReturnStatus, OutReturnStatusEnum.FINISHED.getName()); // 已转预到货单

        } else if (B.isEqual(inOrderInfo.getOrderStatus(), InOrderStatusEnum.PARTIAL_FINISHED.getName())) {

          // 修改入库计划单状态
          lambda1.set(OutReturn::getReturnStatus, OutReturnStatusEnum.PARTIAL_FINISHED.getName()); // 已转预到货单
        } else {
          // 修改入库计划单状态
          lambda1.set(OutReturn::getReturnStatus, OutReturnStatusEnum.CONFIRM_IN.getName()); // 已转预到货单
        }
        lambda1.eq(OutReturn::getReturnCode, inOrderInfo.getSourceCode());
        outReturnService.update(lambda1);

        // 出库退货单
        LambdaQueryWrapper<OutReturn> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OutReturn::getReturnCode, inOrderInfo.getSourceCode());
        OutReturn outReturn = outReturnService.getOne(queryWrapper);


        if (ObjectUtil.isNotEmpty(outReturn)) {
          LambdaQueryWrapper<OutReturnDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(OutReturnDetail::getReturnId, outReturn.getReturnId());
          var outReturnDetails = outReturnDetailService.list(lambdaQueryWrapper);

          for (OutReturnDetail item : outReturnDetails) {
            // 查询对应的预到货单
            LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(InOrderDetail::getSourceDetailId, item.getReturnDetailId());
            var orderDetail = inOrderDetailService.getOne(detailLambdaQueryWrapper);

            LambdaUpdateWrapper<OutReturnDetail> lambda = new UpdateWrapper<OutReturnDetail>().lambda();
            lambda.set(OutReturnDetail::getEnterQuantity, orderDetail.getEnterQuantity()) // 已转预到货单
              .eq(OutReturnDetail::getReturnDetailId, item.getReturnDetailId());
            outReturnDetailService.update(lambda);

            if (ObjectUtil.isNotEmpty(outReturn.getOrderCode())) {
              if (ObjectUtil.isNotEmpty(item.getOrderDetailId())) {
                LambdaUpdateWrapper<OutOrderDetail> detailLambdaUpdateWrapper = new UpdateWrapper<OutOrderDetail>().lambda();
                detailLambdaUpdateWrapper.set(OutOrderDetail::getQuantityRefunded, orderDetail.getEnterQuantity()) // 退货数量
                  .eq(OutOrderDetail::getOrderDetailId, item.getOrderDetailId());
                outOrderDetailService.update(detailLambdaUpdateWrapper);
              }
            }
          }
        }


      }
      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
    }
    return null;
  }
  //#endregion


}

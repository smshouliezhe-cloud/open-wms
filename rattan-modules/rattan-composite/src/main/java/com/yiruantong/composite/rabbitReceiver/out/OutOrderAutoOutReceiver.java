package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OutOrderAutoOutReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IInEnterService inEnterService;
  private final IInEnterDetailService inEnterDetailService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryService coreInventoryService;
  private final IOutSortingRuleService outSortingRuleService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;


  /**
   * 出库单自动出库
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_ORDER_AUTO_OUT); // 接收哪几种类型的数据
  }

  //#region 实现MQ 方法
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
      OutOrder outOrder = outOrderService.getById(rabbitReceiverDto.getBillId());

      //#region 出库单自动出库
      OutScanMainBo newOutScanOrderBo = BeanUtil.copyProperties(outOrder, OutScanMainBo.class);
      newOutScanOrderBo.setScanInType(InventorySourceTypeEnum.INVALIDATE_OUT);
      newOutScanOrderBo.setHolderSourceTypeEnum(HolderSourceTypeEnum.INVALIDATE_OUT);
      List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId()); // 订单明细集合
      newOutScanOrderBo.setDataList(new ArrayList<>()); // 初始化明细
      for (var outOrderDetail : outOrderDetailList) {
        OutScanDetailBo outScanDetailBo = BeanUtil.copyProperties(outOrderDetail, OutScanDetailBo.class);
        outScanDetailBo.setFinishedQuantity(outOrderDetail.getQuantityOrder()); // 扫描完成数量
        newOutScanOrderBo.getDataList().add(outScanDetailBo);
      }
      IOutScanOrderService outScanOrderService = SpringUtils.getBean(IOutScanOrderService.class);
      outScanOrderService.normalOutSave(newOutScanOrderBo);
      //#endregion

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      // 下一步消息队列执行，记录当前业务单号
      this.initNextStep(rabbitReceiverDto, outOrder.getOrderId(), outOrder.getOrderCode());
      return R.ok(rabbitReceiverDto);
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
      return R.fail();
    }
  }
  //#endregion
}

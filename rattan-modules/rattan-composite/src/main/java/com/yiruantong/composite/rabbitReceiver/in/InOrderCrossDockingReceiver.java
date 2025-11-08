package com.yiruantong.composite.rabbitReceiver.in;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.websocket.utils.WebSocketUtils;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预到货单一键入库
 */
@RequiredArgsConstructor
@Service
public class InOrderCrossDockingReceiver implements IRabbitReceiver {

  public final IInOrderPlanStatusHistoryService inOrderPlanStatusHistoryService;
  private final DataSourceTransactionManager transactionManager;
  private final IInOrderService inOrderService;
  private final ITaskQueueService taskQueueService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;
  private final IInScanOrderService inScanOrderService;
  private final IInOrderDetailService inOrderDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_ORDER_CROSS_DOCKING_IN); // 接收哪几种类型的数据
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
      // 预到货单信息
      InOrder inOrder = inOrderService.getById(rabbitReceiverDto.getRootId());
      IInScanOrderService inScanOrderService = SpringUtils.getBean(IInScanOrderService.class);
      Assert.isFalse(!B.isEqual(inOrder.getAuditing(), AuditEnum.AUDITED_SUCCESS.getId()), "只有审核的才允许操作！");
      Assert.isFalse(!B.isEqual(inOrder.getCrossDocking(), NumberUtils.ONE), "只有越库预到货单才允许操作！");

      // 明细项目号默认为预到货单号，用于出库单分拣唯一性标识
      List<InOrderDetail> inOrderDetails = inOrderDetailService.selectListByMainId(inOrder.getOrderId());
      for (var item : inOrderDetails) {
        LambdaUpdateWrapper<InOrderDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        detailLambdaUpdateWrapper.set(InOrderDetail::getProjectCode, inOrder.getOrderCode())
          .eq(InOrderDetail::getOrderDetailId, item.getOrderDetailId());
        inOrderDetailService.update(detailLambdaUpdateWrapper);
      }

      // 一键入库
      inScanOrderService.oneKeyIn(inOrder.getOrderId(), InventorySourceTypeEnum.PC_CROSS_DOCKING_IN);

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      // 完成后，推送消息通知前端
      inOrder = inOrderService.getById(rabbitReceiverDto.getRootId());
      Map<String, Object> otherField = new HashMap<>();
      otherField.put("toStatus", inOrder.getOrderStatus());
      otherField.put("shelveStatus", inOrder.getShelveStatus());
      otherField.put("isLastStep", rabbitReceiverDto.isLastStep()); // 是否为最后一步
      rabbitReceiverDto.setOtherField(otherField);
      WebSocketUtils.publishAll(JsonUtils.toJsonString(rabbitReceiverDto));

      // 下一步消息队列执行，记录当前业务单号
      this.initNextStep(rabbitReceiverDto, inOrder.getOrderId(), inOrder.getOrderCode());
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

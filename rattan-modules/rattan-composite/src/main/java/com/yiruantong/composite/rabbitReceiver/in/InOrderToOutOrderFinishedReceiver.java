package com.yiruantong.composite.rabbitReceiver.in;


import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderActionEnum;
import com.yiruantong.common.core.enums.in.InOrderStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.websocket.utils.WebSocketUtils;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.service.in.IInOrderPlanStatusHistoryService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
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
 * 预到货转出库单自动完成后，更新预到货单状态
 */
@RequiredArgsConstructor
@Service
public class InOrderToOutOrderFinishedReceiver implements IRabbitReceiver {

  public final IInOrderPlanStatusHistoryService inOrderPlanStatusHistoryService;
  private final DataSourceTransactionManager transactionManager;
  private final IInOrderService inOrderService;
  private final ITaskQueueService taskQueueService;
  private final IInOrderStatusHistoryService inOrderStatusHistoryService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_ORDER_TO_OUT_ORDER_FINISHED); // 接收哪几种类型的数据
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
      LoginUser loginUser = LoginHelper.getLoginUser();
      inOrderService.updateStatus(inOrder.getOrderId(), InOrderStatusEnum.TO_OUT_ORDER_FINISHED);
      //添加预到货单轨迹
      InOrderStatusEnum fromInOrderStatusEnum = InOrderStatusEnum.matchingEnum(inOrder.getOrderStatus()); // 开始状态
      inOrderStatusHistoryService.addHistoryInfo(inOrder, InOrderActionEnum.PC_TO_OUT_ORDER_FINISHED, fromInOrderStatusEnum, InOrderStatusEnum.TO_OUT_ORDER_FINISHED);

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      // 完成后，推送消息通知前端
      Map<String, Object> otherField = new HashMap<>();
      otherField.put("toStatus", InOrderStatusEnum.TO_OUT_ORDER_FINISHED.getName());
      otherField.put("isLastStep", rabbitReceiverDto.isLastStep()); // 是否为最后一步
      rabbitReceiverDto.setOtherField(otherField);
      rabbitReceiverDto.setBillId(rabbitReceiverDto.getBillId());
      rabbitReceiverDto.setBillCode(rabbitReceiverDto.getBillCode());
      WebSocketUtils.publishAll(JsonUtils.toJsonString(rabbitReceiverDto));

      // 下一步消息队列执行，记录当前业务单号
      this.initNextStep(rabbitReceiverDto, rabbitReceiverDto.getBillId(), rabbitReceiverDto.getBillCode());
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

package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.task.ITaskQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

/**
 * 出库单分拣后自动生成TMS 运单
 */
@RequiredArgsConstructor
@Service
public class sortingSaveOrderSetReleaseReceiver implements IRabbitReceiver {
  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderSortingService outOrderSortingService;
  private final IOutOrderService outOrderService;

  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.SORTING_SAVE_ORDER_SET_RELEASE); // 接收哪几种类型的数据
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

      Map<String, Object> expandFields = outOrder.getExpandFields();
      if (ObjectUtil.isNull(expandFields)) {
        expandFields = new HashMap<>();
      }
      expandFields.put("release", true);

      LambdaUpdateWrapper<OutOrder> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(OutOrder::getExpandFields, JsonUtils.toJsonString(expandFields))
        .eq(OutOrder::getOrderId, outOrder.getOrderId());
      outOrderService.update(updateWrapper);
      OutOrderStatusEnum outOrderStatusEnum = OutOrderStatusEnum.matchingEnum(outOrder.getOrderStatus());
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.ORDER_OPERATE, outOrderStatusEnum, "紧急订单");

      // 更新任务状态为完成
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
      transactionManager.commit(transaction); // 手动提交事务

      //#endregion
    } catch (Exception e) {
      // 更新任务状态为失败
      transactionManager.rollback(transaction); // 手动回滚事务
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
    }
    return R.ok();
  }

//#endregion
}

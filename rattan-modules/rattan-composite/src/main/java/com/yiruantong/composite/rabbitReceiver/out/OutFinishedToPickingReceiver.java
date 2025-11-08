package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.out.OutOrderTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.List;

import static com.yiruantong.common.satoken.utils.LoginHelper.getLoginUser;

/**
 * 出库完成后回更出库计划单
 */
@RequiredArgsConstructor
@Service
public class OutFinishedToPickingReceiver implements IRabbitReceiver {

  private final IOutOrderSortingService outOrderSortingService;
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderService outOrderService;
  private final ITaskQueueService taskQueueService;
  private final IOutOrderDetailService outOrderDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_FINISHED_TO_PICKING); // 接收哪几种类型的数据
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
      // 出库单信息
      OutOrder outOrderInfo = outOrderService.getById(rabbitReceiverDto.getBillId());

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

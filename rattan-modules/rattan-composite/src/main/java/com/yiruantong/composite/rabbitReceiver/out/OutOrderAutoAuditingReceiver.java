package com.yiruantong.composite.rabbitReceiver.out;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.system.LockNameEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.redisson.api.RLock;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 出库单自动审核
 */
@RequiredArgsConstructor
@Service
public class OutOrderAutoAuditingReceiver implements IRabbitReceiver {
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderWaveService outOrderWaveService;
  private final ISysConfigService sysConfigService;
  private final DataSourceTransactionManager transactionManager;
  private final ITaskQueueService taskQueueService;
  private final IOutOrderService outOrderService;

  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_ORDER_AUTO_AUDITING); // 接收哪几种类型的数据
  }

  /**
   * 执行分拣操作，审核后自动生成波次单
   *
   * @param rabbitReceiverDto 传输数据字典
   * @return
   */
  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    // 在线程里面确保登录信息可用
    LoginHelper.setLoginUser(rabbitReceiverDto.getLoginUser());
    TenantHelper.setDynamic(rabbitReceiverDto.getLoginUser().getTenantId());

    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }
    RLock lock = RedisUtils.getClient().getLock(LockNameEnum.OUT_ORDER_AUTO_AUDITING.getName());
    try {
      lock.lock(20, TimeUnit.SECONDS);
      // 开启事务
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
      definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus transaction = transactionManager.getTransaction(definition);
      try {
        Long orderId = rabbitReceiverDto.getBillId();
        OutOrder outOrder = outOrderService.getBaseMapper().selectById(orderId);
        // 更新出库单审核状态
        LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderLambdaUpdateWrapper.set(OutOrder::getAuditDate, DateUtils.getNowDate())
          .set(OutOrder::getOrderStatus, AuditEnum.AUDITED_SUCCESS.getName())
          .set(OutOrder::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
          .set(OutOrder::getAuditor, LoginHelper.getNickname())
          .eq(OutOrder::getOrderId, outOrder.getOrderId());
        outOrderService.update(orderLambdaUpdateWrapper);

        // 消息队列状态更新
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
    } finally {
      lock.unlock();
    }
  }
  //#endregion
}




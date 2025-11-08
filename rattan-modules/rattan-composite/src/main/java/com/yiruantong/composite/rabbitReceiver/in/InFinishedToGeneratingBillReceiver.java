package com.yiruantong.composite.rabbitReceiver.in;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.system.LockNameEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.inbound.domain.in.InEnter;
import com.yiruantong.inbound.domain.in.InEnterDetail;
import com.yiruantong.inbound.service.in.IInEnterDetailService;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.redisson.api.RLock;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 入库完成后生成每日账单
 */
@RequiredArgsConstructor
@Service
public class InFinishedToGeneratingBillReceiver implements IRabbitReceiver {

  private final ITaskQueueService taskQueueService;
  private final DataSourceTransactionManager transactionManager;
  private final IInEnterService inEnterService;
  private final IInEnterDetailService inEnterDetailService;

  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_FINISHED_TO_GENERATING_BILL); // 接收哪几种类型的数据
  }

  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {// 手动开启事务  start
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }
    RLock lock = RedisUtils.getClient().getLock(LockNameEnum.IN_FINISHED_TO_GENERATING_BILL.getName());
    try {
      lock.lock(20, TimeUnit.SECONDS);
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
      definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus transaction = transactionManager.getTransaction(definition);
      try {
        InEnter enterInfo = inEnterService.getById(rabbitReceiverDto.getBillId());
        Assert.isFalse(ObjectUtil.isNull(enterInfo), "未找到入库单");
        Assert.isFalse(StrUtil.isEmpty(enterInfo.getFeeItemIds()), "预到货单未设置费用项");

        // 构建数据 - 主表数据
        CommonMainDto commonMainDto = BeanUtil.copyProperties(enterInfo, CommonMainDto.class);
        commonMainDto.setMainId(enterInfo.getEnterId());
        commonMainDto.setMainCode(enterInfo.getEnterCode());
        commonMainDto.setSourceId(enterInfo.getOrderId().toString());
        commonMainDto.setSourceCode(enterInfo.getOrderCode());
        commonMainDto.setTotalInQuantity(enterInfo.getTotalEnterQuantity());

        //构建数据 - 明细表数据
        LambdaQueryWrapper<InEnterDetail> enterDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        enterDetailLambdaQueryWrapper.eq(InEnterDetail::getEnterId, enterInfo.getEnterId());
        List<InEnterDetail> enterDetails = inEnterDetailService.list(enterDetailLambdaQueryWrapper);
        Assert.isFalse(enterDetails.isEmpty(), "入库单明细为空，无法生成账单");

        // 更新任务状态为完成
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED);
        transactionManager.commit(transaction); // 手动提交事务
      } catch (Exception e) {
        // 更新任务状态为失败
        transactionManager.rollback(transaction); // 手动回滚事务
        taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, e.getMessage());
      }
    } finally {
      lock.unlock();
    }
    return R.ok();
  }
}

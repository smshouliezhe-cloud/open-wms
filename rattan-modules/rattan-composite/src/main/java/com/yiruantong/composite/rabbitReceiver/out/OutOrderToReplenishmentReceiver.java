package com.yiruantong.composite.rabbitReceiver.out;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.inventory.StorageReplenishmentActionEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentDetailService;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
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
 * 缺货订单自动生成补货单
 */
@RequiredArgsConstructor
@Service
public class OutOrderToReplenishmentReceiver implements IRabbitReceiver {
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderService outOrderService;
  private final ITaskQueueService taskQueueService;
  private final IStorageReplenishmentService storageReplenishmentService;
  private final IStorageReplenishmentDetailService storageReplenishmentDetailService;
  private final IOutOrderDetailService outOrderDetailService;


  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_ORDER_TO_REPLENISHMENT); // 接收哪几种类型的数据
  }

  //#region 实现MQ 方法
  @Override
  public R<RabbitReceiverDto> rabbitReceiver(RabbitReceiverDto rabbitReceiverDto) {
    if (taskQueueService.checkTaskFinished(rabbitReceiverDto.getTaskId())) {
      taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED, MessageUtils.message("rabbitmq.no.longer.executing"));
      return R.fail(MessageUtils.message("rabbitmq.no.longer.executing"));
    }
    RLock lock = RedisUtils.getClient().getLock("rabbitReceiver-to-replenishment");
    try {
      lock.lock(20, TimeUnit.SECONDS);
      // 手动开启事务  start
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
      definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus transaction = transactionManager.getTransaction(definition);
      try {
        OutOrder outOrder = outOrderService.getById(rabbitReceiverDto.getBillId()); // 出库单主表信息
        Assert.isTrue(ObjectUtil.isNotEmpty(outOrder), "未找到对应的出库订单");


        //#region 生成补货单
        StorageReplenishment replenishment = new StorageReplenishment();
        BeanUtil.copyProperties(outOrder, replenishment);
        replenishment.setReplenishmentCode(DBUtils.getCodeRegular(MenuEnum.MENU_2235));
        replenishment.setBillType(StorageReplenishmentActionEnum.LACKSTORAGE_REPLENISHMENT.getName()); // 缺货紧急补货
        replenishment.setSortingStatus(SortingStatusEnum.NONE.getId());
        replenishment.setSourceType(StorageReplenishmentActionEnum.LACKSTORAGE_REPLENISHMENT.getName()); // 缺货紧急补货
        replenishment.setAuditing(AuditEnum.AUDIT.getId());

        storageReplenishmentService.save(replenishment);

        //#endregion

        // 查询明细
        LambdaQueryWrapper<OutOrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OutOrderDetail::getOrderId, outOrder.getOrderId());
        List<OutOrderDetail> outOrderDetails = outOrderDetailService.list(lambdaQueryWrapper);
        //#region 生成补货单明细
        for (var item : outOrderDetails) {
          StorageReplenishmentDetail replenishmentDetail = new StorageReplenishmentDetail();
          BeanUtil.copyProperties(item, replenishmentDetail);
          replenishmentDetail.setReplenishmentId(replenishment.getReplenishmentId());
          storageReplenishmentDetailService.save(replenishmentDetail);
        }
        //#endregion
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

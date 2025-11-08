package com.yiruantong.composite.rabbitReceiver.out;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.system.LockNameEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.redisson.api.RLock;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 出库单分拣
 */
@RequiredArgsConstructor
@Service
public class OutOrderSortingReceiver implements IRabbitReceiver {
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderWaveService outOrderWaveService;
  private final ISysConfigService sysConfigService;
  private final DataSourceTransactionManager transactionManager;
  private final ITaskQueueService taskQueueService;
  private final IOutOrderService outOrderService;

  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_ORDER_SORTING); // 接收哪几种类型的数据
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
    RLock lock = RedisUtils.getClient().getLock(LockNameEnum.OUT_ORDER_SORTING.getName());
    try {
      lock.lock(60, TimeUnit.SECONDS);
      // 开启事务
      DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
      definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
      TransactionStatus transaction = transactionManager.getTransaction(definition);
      try {
        IOutOrderSortingService sortingBean = SpringUtils.getBean(IOutOrderSortingService.class); // 需要动态获取bean，否则循环冲突

        Long orderId = rabbitReceiverDto.getBillId();
        OutOrder outOrder = outOrderService.getBaseMapper().selectById(orderId);
        if (ObjectUtil.isNull(outOrder.getStorageId())) {
          // 更新任务状态为完成
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, outOrder.getOrderCode() + "仓库不能为空");
          transactionManager.commit(transaction); // 手动提交事务
          return R.fail();
        }
        String[] arr = new String[]{OutOrderStatusEnum.STOPED.getName()};

        if (Arrays.asList(arr).contains(outOrder.getOrderStatus())) {
          // 更新任务状态为完成
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "出库单【" + outOrder.getOrderCode() + "】状态为【" + outOrder.getOrderStatus() + "】不允许进行审核操作！");
          transactionManager.commit(transaction); // 手动提交事务
          return R.fail();
        }
        if (NumberUtils.equals(outOrder.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId())) {
          // 更新任务状态为完成
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, "出库单【" + outOrder.getOrderCode() + "】已经分拣成功不允许再次分拣");
          transactionManager.commit(transaction); // 手动提交事务
          return R.fail();
        }

        LambdaQueryWrapper<OutOrderDetail> outOrderWrapper = new LambdaQueryWrapper<>();
        outOrderWrapper.eq(OutOrderDetail::getOrderId, orderId);
        long count = outOrderDetailService.count(outOrderWrapper);
        if (count == 0) {
          // 更新任务状态为完成
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, outOrder.getOrderCode() + "请填写订单明细！");
          transactionManager.commit(transaction); // 手动提交事务
          return R.fail();
        }

        List<OutOrderDetail> outOrderDetail = outOrderDetailService.getBaseMapper().selectList(
          new LambdaQueryWrapper<OutOrderDetail>().eq(OutOrderDetail::getOrderId, orderId)
        );
        if (outOrderDetail.stream().allMatch(f -> f.getQuantityOrder().compareTo(Convert.toBigDecimal(0)) <= 0)) {
          // 更新任务状态为失败
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, outOrder.getOrderCode() + "订单的明细预出库数量不允许小于零！");
          transactionManager.commit(transaction); // 手动提交事务
          return R.fail();
        }

        //执行分拣操作
        R<Void> sortingResult = sortingBean.sorting(orderId, false);// 更新任务状态为完成
        if (!sortingResult.isResult()) {
          // 更新任务状态为失败
          taskQueueService.updateStatus(rabbitReceiverDto.getTaskId(), TaskQueueStatusEnum.IMPLEMENTATION_FAIL, outOrder.getOrderCode() + sortingResult.getMsg());
          sortingBean.computeLackStorage(orderId);
          transactionManager.commit(transaction); // 手动提交事务
          return R.fail();
        }

        // 审核后自动生成波次单
        boolean batch_onlyExamineAfter = sysConfigService.getConfigBool("batch_onlyExamineAfter");
        if (batch_onlyExamineAfter) {
          outOrder = outOrderService.getBaseMapper().selectById(orderId);
          if (NumberUtils.equals(outOrder.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId())) {
            Map<String, Object> map = new HashMap<>();
            map.put("orderCount", 1);
            map.put("ids", orderId);
            outOrderWaveService.createOrderWave(map);
          }
        }
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




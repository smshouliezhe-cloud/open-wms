package com.yiruantong.composite.rabbitReceiver.out;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.out.OutOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.out.OutSourceTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.OutOrderPlanDetail;
import com.yiruantong.outbound.service.out.*;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出库完成后回更出库计划单
 */
@RequiredArgsConstructor
@Service
public class OutFinishedToPlanReceiver implements IRabbitReceiver {

  private final IOutOrderSortingService outOrderSortingService;
  private final DataSourceTransactionManager transactionManager;
  private final IOutOrderService outOrderService;
  private final ITaskQueueService taskQueueService;
  private final IOutOrderDetailService outOrderDetailService;
  private final IOutOrderPlanService outOrderPlanService;
  private final IOutOrderPlanDetailService outOrderPlanDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.OUT_FINISHED_TO_PLAN); // 接收哪几种类型的数据
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


      // 如果入库单状态为出库退货转入  操作完入库后的状态要更新到出库退货单
      if (B.isEqual(outOrderInfo.getSourceType(), OutSourceTypeEnum.OUT_PLANORDER.getName())) {
        // 出库退货单
        LambdaQueryWrapper<OutOrderPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OutOrderPlan::getOrderPlanCode, outOrderInfo.getSourceCode());
        OutOrderPlan outOrderPlan = outOrderPlanService.getOne(queryWrapper);


        if (ObjectUtil.isNotEmpty(outOrderPlan)) {
          LambdaQueryWrapper<OutOrderPlanDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(OutOrderPlanDetail::getOrderPlanId, outOrderPlan.getOrderPlanId());
          var outOrderPlanDetails = outOrderPlanDetailService.list(lambdaQueryWrapper);

          for (OutOrderPlanDetail item : outOrderPlanDetails) {
            // 查询对应的预到货单
            LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(OutOrderDetail::getSourceDetailId, item.getOrderPlanDetailId())
              .eq(OutOrderDetail::getOrderId, outOrderInfo.getOrderId());
            var orderDetail = outOrderDetailService.getOne(detailLambdaQueryWrapper);

            LambdaUpdateWrapper<OutOrderPlanDetail> lambda = new UpdateWrapper<OutOrderPlanDetail>().lambda();
            lambda.set(OutOrderPlanDetail::getOutQuantity, orderDetail.getQuantityOuted()) // 已出库数量
              .set(OutOrderPlanDetail::getSurplusQuantity, B.sub(item.getQuantity(), orderDetail.getQuantityOuted())) // 未出库数量
              .eq(OutOrderPlanDetail::getOrderPlanDetailId, item.getOrderPlanDetailId());
            outOrderPlanDetailService.update(lambda);
          }
          LambdaQueryWrapper<OutOrderPlanDetail> planDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
          planDetailLambdaQueryWrapper.eq(OutOrderPlanDetail::getOrderPlanId, outOrderPlan.getOrderPlanId());
          var orderPlanDetails = outOrderPlanDetailService.list(lambdaQueryWrapper);
          //预出库数量总和
          var totalQuantity = orderPlanDetails.stream().map(OutOrderPlanDetail::getQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);
          // 已出库数量总和
          var outQuantity = orderPlanDetails.stream().map(OutOrderPlanDetail::getOutQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);


          LambdaUpdateWrapper<OutOrderPlan> lambda1 = new UpdateWrapper<OutOrderPlan>().lambda();
          if (B.isGreaterOrEqual(outQuantity, totalQuantity)) {
            //已出库
            lambda1.set(OutOrderPlan::getPlanStatus, OutOrderPlanStatusEnum.OUT_FINISHED.getName()); // 已出库
          } else if (B.isGreater(outQuantity) && B.isLess(outQuantity, totalQuantity)) {
            // 部分出库
            lambda1.set(OutOrderPlan::getPlanStatus, OutOrderPlanStatusEnum.PARTIAL_TO_OUT.getName()); // 部分出库

          }
          lambda1.eq(OutOrderPlan::getOrderPlanCode, outOrderInfo.getSourceCode());
          outOrderPlanService.update(lambda1);
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
    return R.ok();
  }
}

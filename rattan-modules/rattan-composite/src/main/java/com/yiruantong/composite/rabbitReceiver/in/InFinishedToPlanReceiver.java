package com.yiruantong.composite.rabbitReceiver.in;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.in.InOrderPlanActionEnum;
import com.yiruantong.common.core.enums.in.InOrderPlanStatusEnum;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IRabbitReceiver;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.InOrderPlanDetail;
import com.yiruantong.inbound.service.in.*;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.List;

/**
 * 入库完成回更入库计划单
 */
@RequiredArgsConstructor
@Service
public class InFinishedToPlanReceiver implements IRabbitReceiver {

  public final IInOrderPlanStatusHistoryService inOrderPlanStatusHistoryService;
  private final IOutOrderSortingService outOrderSortingService;
  private final DataSourceTransactionManager transactionManager;
  private final IInOrderService inOrderService;
  private final ITaskQueueService taskQueueService;
  private final IInOrderDetailService inOrderDetailService;
  private final IInOrderPlanService inOrderPlanService;
  private final IInOrderPlanDetailService inOrderPlanDetailService;

  /**
   * 标注接收的数据类型
   *
   * @return 结果
   */
  @Override
  public List<RabbitmqTypeEnum> getType() {
    return List.of(RabbitmqTypeEnum.IN_FINISHED_TO_PLAN); // 接收哪几种类型的数据
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
      // 入库单信息
      InOrder inOrderInfo = inOrderService.getById(rabbitReceiverDto.getBillId());
      LoginUser loginUser = LoginHelper.getLoginUser();


      // 如果入库单状态为计划入库转入  操作完入库后的状态要更新到入库计划单
      if (B.isEqual(inOrderInfo.getSourceType(), InOrderTypeEnum.ORDER_PLAN.getName())) {
        // 入库计划单
        LambdaQueryWrapper<InOrderPlan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InOrderPlan::getPlanCode, inOrderInfo.getSourceCode());
        InOrderPlan inOrderPlan = inOrderPlanService.getOne(queryWrapper);


        if (ObjectUtil.isNotEmpty(inOrderPlan)) {
          LambdaQueryWrapper<InOrderPlanDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
          lambdaQueryWrapper.eq(InOrderPlanDetail::getPlanId, inOrderPlan.getPlanId());
          var inOrderPlanDetails = inOrderPlanDetailService.list(lambdaQueryWrapper);

          for (InOrderPlanDetail item : inOrderPlanDetails) {
            // 查询对应的预到货单
            LambdaQueryWrapper<InOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(InOrderDetail::getSourceDetailId, item.getPlanDetailId())
              .eq(InOrderDetail::getOrderId, inOrderInfo.getOrderId());
            var orderDetail = inOrderDetailService.getOne(detailLambdaQueryWrapper);

            BigDecimal inQuantity = BigDecimal.ZERO;
            if (ObjectUtil.isNotNull(inQuantity)) {
              inQuantity = orderDetail.getEnterQuantity();
            } else {
              inQuantity = BigDecimal.ZERO;
            }
            LambdaUpdateWrapper<InOrderPlanDetail> lambda = new UpdateWrapper<InOrderPlanDetail>().lambda();
            lambda.set(InOrderPlanDetail::getInQuantity, inQuantity) // 已收货数量
              .set(InOrderPlanDetail::getSurplusQuantity, B.sub(item.getQuantityOrder(), orderDetail.getEnterQuantity())) // 未入库数量
              .eq(InOrderPlanDetail::getPlanDetailId, item.getPlanDetailId());
            inOrderPlanDetailService.update(lambda);
          }
          LambdaQueryWrapper<InOrderPlanDetail> planDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
          planDetailLambdaQueryWrapper.eq(InOrderPlanDetail::getPlanId, inOrderPlan.getPlanId());
          var orderPlanDetails = inOrderPlanDetailService.list(lambdaQueryWrapper);
          //预入库数量总和
          var totalQuantity = orderPlanDetails.stream().map(InOrderPlanDetail::getQuantityOrder).reduce(BigDecimal.ZERO, BigDecimal::add);
          // 已入库数量总和
          var outQuantity = orderPlanDetails.stream().map(InOrderPlanDetail::getInQuantity).reduce(BigDecimal.ZERO, BigDecimal::add);


          LambdaUpdateWrapper<InOrderPlan> lambda1 = new UpdateWrapper<InOrderPlan>().lambda();
          if (B.isGreaterOrEqual(outQuantity, totalQuantity)) {
            //完全交货
            lambda1.set(InOrderPlan::getPlanStatus, InOrderPlanStatusEnum.IN_FINISHED.getName()); // 完全交货
            //添加轨迹信息
            inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.IN_FINISHED_TO_PLAN.getName(), inOrderPlan.getPlanStatus(), InOrderPlanStatusEnum.IN_FINISHED.getName(), loginUser, null);
          } else if (B.isGreater(outQuantity) && B.isLess(outQuantity, totalQuantity)) {
            // 部分交货
            lambda1.set(InOrderPlan::getPlanStatus, InOrderPlanStatusEnum.PARTIAL_TO_IN.getName()); // 部分交货
            //添加轨迹信息
            inOrderPlanStatusHistoryService.addHistoryInfo(inOrderPlan, InOrderPlanActionEnum.IN_FINISHED_TO_PLAN.getName(), inOrderPlan.getPlanStatus(), InOrderPlanStatusEnum.PARTIAL_TO_IN.getName(), loginUser, null);
          }
          lambda1.eq(InOrderPlan::getPlanCode, inOrderInfo.getSourceCode());
          inOrderPlanService.update(lambda1);
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

package com.yiruantong.outbound.liteflow.OutOrderAudit;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.AuditEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOperationTypeEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.utils.DateUtils;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.liteflow.Context.OutOrderAuditingContext;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderStatusHistoryService;
import com.yiruantong.system.service.task.ITaskQueueService;

import java.util.List;

@LiteflowComponent(id = "OutAuditCmp", name = "出库审核")
@RequiredArgsConstructor
public class OutAuditCmp extends NodeComponent {


  private final ITaskQueueService taskQueueService;
  private final IOutOrderService outOrderService;
  private final IOutOrderStatusHistoryService outOrderStatusHistoryService;

  @Override
  public void process() {

    List<OutOrder> outOrders = this.getRequestData();
    OutOrderAuditingContext ctx = this.getContextBean(OutOrderAuditingContext.class);

    ctx.setOutOrders(outOrders);
    List<OutOrder> outOrderBo = ctx.getOutOrders();

    for (OutOrder outOrder : outOrderBo) {
      Assert.isFalse(StrUtil.equals(outOrder.getOrderStatus(), AuditEnum.AUDITED_SUCCESS.getName()), outOrder.getOrderCode() + "单据已经审核，请刷新页面！");
      // 更新出库单审核状态
      LambdaUpdateWrapper<OutOrder> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      orderLambdaUpdateWrapper.set(OutOrder::getAuditDate, DateUtils.getNowDate())
        .set(OutOrder::getOrderStatus, AuditEnum.AUDITED_SUCCESS.getName())
        .set(OutOrder::getAuditing, AuditEnum.AUDITED_SUCCESS.getId())
        .set(OutOrder::getAuditor, LoginHelper.getNickname())
        .eq(OutOrder::getOrderId, outOrder.getOrderId());
      outOrderService.update(orderLambdaUpdateWrapper);

      // 出库单的轨迹
      outOrderStatusHistoryService.AddHistory(outOrder, OutOperationTypeEnum.AUDITING, OutOrderStatusEnum.AUDIT_SUCCESS);

      //调用RabbitMQ，审核完成后，自动分拣、自动生成波次
      RabbitReceiverDto receiverDto = new RabbitReceiverDto();
      receiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_SORTING); // 审核完成后，自动分拣、自动生成波次
      receiverDto.setBillId(outOrder.getOrderId());
      receiverDto.setBillCode(outOrder.getOrderCode());
      taskQueueService.createTask(receiverDto);

      /**
       * 缺货后自动生成补货单
       */
      if (outOrder.getSortingStatus().equals(SortingStatusEnum.LACK.getId())) {
        //调用RabbitMQ
        RabbitReceiverDto rabbitReceiverDto = new RabbitReceiverDto();
        rabbitReceiverDto.setRabbitmqType(RabbitmqTypeEnum.OUT_ORDER_TO_REPLENISHMENT); // 缺货单转补货单
        rabbitReceiverDto.setBillId(outOrder.getOrderId());
        rabbitReceiverDto.setBillCode(outOrder.getOrderCode());
        rabbitReceiverDto.setSourceCode(outOrder.getSourceCode());
        rabbitReceiverDto.setSourceId(outOrder.getSourceId());
        taskQueueService.createTask(rabbitReceiverDto);
      }
      //#endregion
    }
  }

}

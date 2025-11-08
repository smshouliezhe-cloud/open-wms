package com.yiruantong.outbound.liteflow.OutOrderAudit;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.out.OutSourceTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.liteflow.Context.OutOrderAuditingContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;

import java.util.List;

@LiteflowComponent(id = "OrderAuditingBeforeCmp", name = "出库审核前判断")
@RequiredArgsConstructor
public class OrderAuditingBeforeCmp extends NodeComponent {

  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;

  @Override
  public void process() {

    List<OutOrder> outOrders = this.getRequestData();
    OutOrderAuditingContext ctx = this.getContextBean(OutOrderAuditingContext.class);
    ctx.setOutOrders(outOrders);
    //判断
    Boolean isOk = true;
    for (OutOrder outOrder : outOrders) {
      if (ObjectUtils.isNotEmpty(outOrder.getClientShortName()) && ObjectUtils.isNotEmpty(outOrder.getStorageName()) && ObjectUtils.isNotEmpty(outOrder.getConsignorName())) {
        // 要审核的单据明细
        LambdaQueryWrapper<OutOrderDetail> detailBeforeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        detailBeforeLambdaQueryWrapper.eq(OutOrderDetail::getOrderId, outOrder.getOrderId());
        List<OutOrderDetail> outBeforeDetails = outOrderDetailService.list(detailBeforeLambdaQueryWrapper);
        // 查询客户一样的所有拆分单据
        LambdaQueryWrapper<OutOrder> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(OutOrder::getClientShortName, outOrder.getClientShortName())
          .eq(OutOrder::getStorageName, outOrder.getStorageName())
          .eq(OutOrder::getConsignorName, outOrder.getConsignorName())
          .eq(OutOrder::getSourceType, OutSourceTypeEnum.SPLIT_CREATE.getName());
        List<OutOrder> orderList = outOrderService.list(orderLambdaQueryWrapper);

        if (ObjectUtils.isNotEmpty(orderList)) {

          for (OutOrder item : orderList) {
            // 拆分单据的明细
            LambdaQueryWrapper<OutOrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
            detailLambdaQueryWrapper.eq(OutOrderDetail::getOrderId, item.getOrderId());
            List<OutOrderDetail> outOrderDetails = outOrderDetailService.list(detailLambdaQueryWrapper);

            for (OutOrderDetail items : outBeforeDetails) {
              if (!outOrderDetails.stream().filter(f -> B.isEqual(items.getProductCode(), f.getProductCode())).toList().isEmpty()) {
                isOk = false;
              }
            }
          }
        }
      }
    }

    Assert.isFalse(!isOk, "当前收货客户存在待合并的历史差异单,请先操作订单合并！");
  }
}



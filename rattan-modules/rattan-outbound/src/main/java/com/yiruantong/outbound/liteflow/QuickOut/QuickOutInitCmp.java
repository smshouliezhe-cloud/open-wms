package com.yiruantong.outbound.liteflow.QuickOut;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.QuickOutContext;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import com.yiruantong.outbound.service.out.IOutScanOrderService;

@LiteflowComponent(id = "quickOutInitCmp", name = "1.初始化数据，并且把对应的数据添加到上下文中")
@RequiredArgsConstructor
public class QuickOutInitCmp extends NodeComponent {
  private final IOutOrderService outOrderService;
  private final IOutScanOrderService outScanOrderService;
  private final IOutOrderSortingService outOrderSortingService;


  @Override
  public void process() {
    OutScanMainBo outScanMainBo = this.getRequestData();
    QuickOutContext ctx = this.getContextBean(QuickOutContext.class);
    ctx.setOutScanMainBo(outScanMainBo);

    OutOrder outOrder = outOrderService.getBaseMapper().selectById(outScanMainBo.getOrderId());

    ctx.setOutOrder(outOrder);
  }
}

package com.yiruantong.outbound.liteflow.OutScanCmp;

import cn.hutool.core.util.ObjectUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;

@LiteflowComponent(id = "outScanUpPurchaseCmp", name = "7.更新采购单状态")
@RequiredArgsConstructor
public class OutScanUpPurchaseCmp extends NodeComponent {

  @Override
  public void process() {
    OutScanContext ctx = this.getContextBean(OutScanContext.class);
    OutOrder outOrder = ctx.getOutOrder();

    // 更新采购单状态
  }
}

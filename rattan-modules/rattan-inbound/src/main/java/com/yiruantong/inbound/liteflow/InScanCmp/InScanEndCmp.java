package com.yiruantong.inbound.liteflow.InScanCmp;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.enums.in.InOrderTypeEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.InOrderDetail;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.domain.in.bo.InScanOrderDetailBo;
import com.yiruantong.inbound.liteflow.Context.InScanContext;
import com.yiruantong.inbound.service.in.IInEnterService;
import com.yiruantong.inbound.service.in.IInOrderDetailService;
import com.yiruantong.inbound.service.in.IInOrderService;
import com.yiruantong.system.service.core.ISysConfigService;

import java.util.List;

@LiteflowComponent(id = "inScanEndCmp", name = "收尾处理")
@RequiredArgsConstructor
public class InScanEndCmp extends NodeComponent {
  private final IInOrderService inOrderService;
  private final IInOrderDetailService inOrderDetailService;
  private final ISysConfigService sysConfigService;
  private final IBaseStorageService baseStorageService;
  private final IInEnterService inEnterService;

  @Override
  public void process() {
    InScanContext ctx = this.getContextBean(InScanContext.class);
    InScanOrderBo inScanOrderBo = ctx.getInScanOrderBo();

    List<InScanOrderDetailBo> dataList = inScanOrderBo.getDataList(); // 明细JSON集合数据
    InOrder inOrder = ctx.getInOrder();

    // 如果类型是合金/辅料 需要吧 生产日期 默认为当前时间
    for (var detail : dataList) {
      Object tareWeight = detail.getExpandFields().get("tareWeight");
      Object rowTareWeight = detail.getExpandFields().get("rowTareWeight");
      // 回更 生产日期
      LambdaUpdateWrapper<InOrderDetail> updateWrapper = new LambdaUpdateWrapper<>();
      updateWrapper.set(InOrderDetail::getParcelQuantity, detail.getParcelQuantity()) // 包数
        .set(InOrderDetail::getParcelAverageWeight, detail.getParcelAverageWeight()) // 均重
        .set(InOrderDetail::getProductSpec, detail.getProductSpec()) // 包装规格
        .setSql("expand_fields = json_set(expand_fields,'$.tareWeight', '" + Convert.toStr(tareWeight) + "')")
        .setSql("expand_fields = json_set(expand_fields,'$.rowTareWeight', '" + Convert.toStr(rowTareWeight) + "')")
        .eq(InOrderDetail::getOrderDetailId, detail.getOrderDetailId());
      if (B.isEqual(inOrder.getOrderType(), InOrderTypeEnum.AUXILIARY_MATERIAL.getName())) {
        updateWrapper.set(InOrderDetail::getProduceDate, detail.getProduceDate());
      }
      inOrderDetailService.update(updateWrapper);
    }
  }

}

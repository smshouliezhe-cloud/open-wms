package com.yiruantong.outbound.liteflow.OutScanCmp;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.bo.OutScanDetailBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.liteflow.Context.OutScanContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;

import java.math.BigDecimal;
import java.util.List;

@LiteflowComponent(id = "outScanValidationCmp", name = "2.出库扫描校验组件")
@RequiredArgsConstructor
public class OutScanValidationCmp extends NodeComponent {
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderDetailService outOrderDetailService;

  @Override
  public void process() {
    OutScanMainBo outScanMainBo = this.getRequestData();
    OutScanContext ctx = this.getContextBean(OutScanContext.class);
    List<OutScanDetailBo> dataList = outScanMainBo.getDataList(); // 明细JSON集合数据
    OutOrder outOrder = ctx.getOutOrder();
    Long orderId = outOrder.getOrderId();

    // 校验数据
    Assert.isFalse(ObjectUtil.isEmpty(outOrder), "出库单不存在！");
    Assert.isFalse(!B.isEqual(outOrder.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId()), "出库单未分配，不允许出库！");
    Assert.isFalse(ObjectUtil.isEmpty(outOrder.getStorageId()), "出库单仓库ID不能为空！");

    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());
    Assert.isFalse(holderSourceTypeEnum == null, "未找到出库单的枚举");
    for (OutScanDetailBo outScanDetailBo : dataList) {
      Long orderDetailId = outScanDetailBo.getOrderDetailId();
      OutOrderDetail outOrderDetail = outOrderDetailService.getById(orderDetailId);

      // 校验占位是否正确
      BigDecimal placeholderStorage;
      if (ctx.isOuterPickQuantity())
        placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(orderId, orderDetailId, HolderSourceTypeEnum.OUT_PICKING);
      else {
        placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(orderId, orderDetailId, holderSourceTypeEnum, HolderSourceTypeEnum.OUT_PICKING);
      }

      //批次号存在的话
      if (ObjectUtil.isNotEmpty(outScanDetailBo.getBatchNumber())) {
        // 按拣货数量打包开启
        if (ctx.isOuterPickQuantity())
          placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(orderId, orderDetailId, outScanDetailBo.getBatchNumber(), HolderSourceTypeEnum.OUT_PICKING);
        else {
          placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(orderId, orderDetailId, outScanDetailBo.getBatchNumber(), holderSourceTypeEnum, HolderSourceTypeEnum.OUT_PICKING);
        }
      }
      Assert.isFalse(!B.isEqual(placeholderStorage, outScanDetailBo.getFinishedQuantity()), outScanDetailBo.getBatchNumber() + "批次未找到可以占位的库存");
    }
  }
}

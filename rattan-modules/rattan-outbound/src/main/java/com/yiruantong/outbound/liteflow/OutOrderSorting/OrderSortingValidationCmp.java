package com.yiruantong.outbound.liteflow.OutOrderSorting;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.enums.out.OutOrderStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.liteflow.Context.OrderSortingContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;

import java.util.List;


@LiteflowComponent(id = "orderSortingValidationCmp", name = "2.出库分拣校验")
@RequiredArgsConstructor
public class OrderSortingValidationCmp extends NodeComponent {

  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderSortingService outOrderSortingService;

  @Override
  public void process() throws Exception {
    OrderSortingContext ctx = this.getContextBean(OrderSortingContext.class);

    // 出库单信息
    OutOrder outOrder = outOrderService.getById(ctx.getOrderId());
    if (ObjectUtil.isEmpty(outOrder)) {
      throw new ServiceException("出库单不存在");
    }
    Assert.isTrue(outOrder.getOrderStatus().equals(OutOrderStatusEnum.AUDIT_SUCCESS.getName()), "只有已审核的单据才可以进行分拣");
    Assert.isFalse(B.isEqual(outOrder.getSortingStatus(), SortingStatusEnum.ASSIGNED.getId()), "已经分拣的单据不允许在分拣");
    // 出库单明细集合
    List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(outOrder.getOrderId());

    // 清除占位
    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());
    Assert.isFalse(holderSourceTypeEnum == null, outOrder.getOrderCode() + "未找到出库单的占位类型枚举");

    coreInventoryHolderService.clearHolder(List.of(holderSourceTypeEnum), outOrder.getOrderId());

    // 计算出库单明细缺货数量
    outOrderSortingService.computeLackStorage(outOrderDetailList, holderSourceTypeEnum);

    ctx.setOrderInfo(outOrder);
    ctx.setHolderSourceTypeEnum(holderSourceTypeEnum);
    ctx.setOutOrderDetails(outOrderDetailList);
  }

}

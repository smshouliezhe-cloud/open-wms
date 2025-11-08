package com.yiruantong.outbound.service.out.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.domain.out.OutOrderSorting;
import com.yiruantong.outbound.liteflow.Context.OrderSortingContext;
import com.yiruantong.outbound.service.out.IOutOrderDetailService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import com.yiruantong.outbound.service.out.IOutOrderSortingService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 出库单分拣service
 */
@RequiredArgsConstructor
@Service
public class OutOrderSortingService implements IOutOrderSortingService {
  private final ICoreInventoryHolderService coreInventoryHolderService;
  private final IOutOrderService outOrderService;
  private final IOutOrderDetailService outOrderDetailService;
  @Resource
  private final FlowExecutor flowExecutor;

  //#region 分拣出库单 sorting

  /**
   * 分拣出库单
   *
   * @param orderId 出库单ID
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> sorting(Long orderId) {
    return sorting(orderId, false);
  }

  /**
   * 分拣出库单
   *
   * @param orderId               出库单ID
   * @param isOnlyStoragePosition 仅分拣存储货位
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> sorting(Long orderId, boolean isOnlyStoragePosition) {
    List<InventoryStatusEnum> inventoryStatusEnumList = CollUtil.newArrayList(InventoryStatusEnum.NORMAL);
    return sorting(orderId, isOnlyStoragePosition, inventoryStatusEnumList, null);
  }

  /**
   * 分拣出库单
   *
   * @param orderId               出库单ID
   * @param isOnlyStoragePosition 仅分拣存储货位
   * @return
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> sorting(Long orderId, boolean isOnlyStoragePosition, List<InventoryStatusEnum> inventoryStatusEnumList, List<PositionTypeEnum> positionTypeEnumList) {

    OutOrderSorting outOrderSorting = new OutOrderSorting();
    outOrderSorting.setOrderId(orderId);
    outOrderSorting.setIsOnlyStoragePosition(isOnlyStoragePosition);
    outOrderSorting.setInventoryStatusEnumList(inventoryStatusEnumList);
    outOrderSorting.setPositionTypeEnumList(positionTypeEnumList);
    outOrderSorting.setOrderId(orderId);

    LiteflowResponse response = flowExecutor.execute2Resp("outOrderSortingChain", outOrderSorting, OrderSortingContext.class);
    if (response.isSuccess()) {
      return R.ok("分拣成功");
    } else {
      return R.fail(response.getMessage());
    }
  }


  //#region sortingAsync
  @Async
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void sortingAsync(Long orderId, boolean isOnlyStoragePosition) {
    sorting(orderId, isOnlyStoragePosition);
  }
  //#endregion

  //#region 更新主表分拣状态
  @Override
  public boolean updateMainSorting(OutOrder outOrder, List<OutOrderDetail> outOrderDetailList) {
    boolean isSortingSuccess = false; // 是否分拣成功
    if (outOrderDetailList.stream().allMatch(f -> B.isLessOrEqual(f.getLackStorage()))) {
      // 分拣成功
      outOrderService.updateSortingStatus(outOrder.getOrderId(), SortingStatusEnum.ASSIGNED);
      isSortingSuccess = true;
    } else if (outOrderDetailList.stream().anyMatch(f -> B.isGreater(f.getLackStorage())) && outOrderDetailList.stream().anyMatch(f -> B.isGreater(f.getQuantityOrder(), f.getLackStorage()))) {
      // 部分分配
      outOrderService.updateSortingStatus(outOrder.getOrderId(), SortingStatusEnum.PARTIAL_ASSIGNED);
    } else {
      // 缺货
      outOrderService.updateSortingStatus(outOrder.getOrderId(), SortingStatusEnum.LACK);
    }
    return isSortingSuccess;
  }
  //endregion

  //#region computeLackStorage
  public void computeLackStorage(List<OutOrderDetail> outOrderDetailList, HolderSourceTypeEnum holderSourceTypeEnum) {
    for (var detail : outOrderDetailList) {
      // 获取占位
      BigDecimal placeholderStorage = coreInventoryHolderService.getPlaceholderStorage(detail.getOrderId(), detail.getOrderDetailId(), holderSourceTypeEnum);
      BigDecimal lackStorage = B.sub(detail.getQuantityOrder(), placeholderStorage); // 缺货数量
      if (B.isLess(lackStorage)) lackStorage = BigDecimal.ZERO;

      detail.setLackStorage(lackStorage);
      // 更新缺货数量
      LambdaUpdateWrapper<OutOrderDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      detailLambdaUpdateWrapper.set(OutOrderDetail::getLackStorage, lackStorage)
        // 已分配
        .set(B.isLessOrEqual(lackStorage), OutOrderDetail::getSortingStatus, SortingStatusEnum.ASSIGNED.getId())
        // 部分缺货
        .set(B.isGreater(lackStorage) && B.isGreater(detail.getQuantityOrder(), lackStorage), OutOrderDetail::getSortingStatus, SortingStatusEnum.PARTIAL_ASSIGNED.getId())
        // 缺货
        .set(B.isEqual(detail.getQuantityOrder(), lackStorage), OutOrderDetail::getSortingStatus, SortingStatusEnum.LACK.getId()).eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
      outOrderDetailService.update(detailLambdaUpdateWrapper);
    }
  }

  public void computeLackStorage(Long orderId) {
    OutOrder outOrder = outOrderService.getById(orderId);
    List<OutOrderDetail> outOrderDetailList = outOrderDetailService.selectListByMainId(orderId);
    HolderSourceTypeEnum holderSourceTypeEnum = HolderSourceTypeEnum.matchingEnum(outOrder.getOrderType());
    Assert.isFalse(holderSourceTypeEnum == null, "未找到出库单的枚举");
    this.computeLackStorage(outOrderDetailList, holderSourceTypeEnum);
    this.updateMainSorting(outOrder, outOrderDetailList);

    outOrder = outOrderService.getById(orderId);
    outOrderDetailList = outOrderDetailService.selectListByMainId(orderId);
    for (var detail : outOrderDetailList) {
      if (B.isEqual(detail.getSortingStatus(), SortingStatusEnum.LACK.getId())) {
        // 更新缺货数量
        LambdaUpdateWrapper<OutOrderDetail> detailLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        detailLambdaUpdateWrapper.set(OutOrderDetail::getSortingStatus, SortingStatusEnum.NONE.getId()).eq(OutOrderDetail::getOrderDetailId, detail.getOrderDetailId());
        outOrderDetailService.update(detailLambdaUpdateWrapper);
      }
    }
    if (B.isEqual(outOrder.getSortingStatus(), SortingStatusEnum.LACK.getId())) {
      // 缺货
      outOrderService.updateSortingStatus(outOrder.getOrderId(), SortingStatusEnum.NONE);
    }
  }
  //#endregion

  //#region sortingInventory


}

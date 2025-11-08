package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.util.List;

public interface IOutOrderSortingService {
  /**
   * 分拣出库单
   *
   * @param orderId 出库单ID
   */
  R<Void> sorting(Long orderId);

  /**
   * 分拣出库单
   *
   * @param orderId               出库单ID
   * @param isOnlyStoragePosition 仅分拣存储货位
   */
  R<Void> sorting(Long orderId, boolean isOnlyStoragePosition);

  /**
   * 分拣出库单
   *
   * @param orderId                 出库单ID
   * @param isOnlyStoragePosition   仅分拣存储货位
   * @param inventoryStatusEnumList 可分拣库存状态
   * @param positionTypeEnumList    可分拣货位类型
   */
  R<Void> sorting(Long orderId, boolean isOnlyStoragePosition, List<InventoryStatusEnum> inventoryStatusEnumList, List<PositionTypeEnum> positionTypeEnumList);

  /**
   * 分拣出库单异步
   *
   * @param orderId               出库单ID
   * @param isOnlyStoragePosition 仅分拣存储货位
   */
  void sortingAsync(Long orderId, boolean isOnlyStoragePosition);

  /**
   * 重新计算缺货数量
   *
   * @param outOrderDetailList   出库单明细
   * @param holderSourceTypeEnum 来源类型
   */
  void computeLackStorage(List<OutOrderDetail> outOrderDetailList, HolderSourceTypeEnum holderSourceTypeEnum);

  /**
   * 根据出库单ID重新计算占位
   *
   * @param orderId 出库单ID
   */
  void computeLackStorage(Long orderId);

  /**
   * 更新主表分拣状态
   */
  boolean updateMainSorting(OutOrder outOrder, List<OutOrderDetail> outOrderDetailList);
}

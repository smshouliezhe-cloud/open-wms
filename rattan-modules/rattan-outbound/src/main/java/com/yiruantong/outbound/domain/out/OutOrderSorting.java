package com.yiruantong.outbound.domain.out;

import lombok.Data;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;

import java.util.List;

/**
 * 出库单分拣参数对象 out_order_plan
 *
 * @author YiRuanTong
 * @date 2024-10-31
 */
@Data
public class OutOrderSorting {
  /**
   * 出库单ID
   */
  Long orderId;

  /**
   * 仅分拣存储货位
   */
  Boolean isOnlyStoragePosition;

  /**
   * 库存状态枚举集合
   */
  List<InventoryStatusEnum> inventoryStatusEnumList;

  /**
   * 货位类型枚举集合
   */
  List<PositionTypeEnum> positionTypeEnumList;
}

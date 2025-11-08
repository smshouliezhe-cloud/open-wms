package com.yiruantong.outbound.liteflow.Context;

import lombok.Data;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventoryStatusEnum;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutOrderDetail;

import java.util.List;


@Data
public class OrderSortingContext {
  /**
   * 出库单Id
   */
  Long orderId;

  /**
   * 出库单主表信息
   */
  OutOrder orderInfo;

  /**
   * 出库单明细集合
   */
  List<OutOrderDetail> outOrderDetails;

  /**
   * 仅分拣存储货位
   */
  Boolean isOnlyStoragePosition;

  /**
   * 出库单占位类型枚举
   */
  HolderSourceTypeEnum holderSourceTypeEnum;

  /**
   * 库存状态枚举集合
   */
  List<InventoryStatusEnum> inventoryStatusEnumList;

  /**
   * 货位类型枚举集合
   */
  List<PositionTypeEnum> positionTypeEnumList;
  /**
   * 是否整货位拣配单
   */
  Boolean sorting_isFullPositionLoad;

  /**
   * 是否跨货主分拣
   */
  Boolean sorting_crossConsignor;

  /**
   * 是否跨货主分拣(天数)
   */
  Long sorting_crossConsignorDays;

  /**
   * 是否整箱拣配单
   */
  Boolean sorting_isFullContainerLoad;

  /**
   * 分拣订单时启用停售提前时长
   */
  Boolean batch_sorting_stopSaleday;

  /**
   * 分拣时货位顺序优先于入库时间
   */
  Boolean sorting_positionPriorIndate;

  /**
   * 开启库区分拣策略
   */
  Boolean batch_openStorageArea_regular;

  /**
   * 出库按照唯一码进行分拣
   */
  Boolean sorting_singleSignCode;

  /**
   * 是否按集装箱分拣
   */
  Boolean sorting_isContainerNo;

  /**
   * 货位数量更多优先分拣
   */
  Boolean sorting_positionGreaterQty;
}

package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存状态枚举
 */
@Getter
@AllArgsConstructor
public enum InventoryStatusEnum {
  /**
   * 正常
   */
  NORMAL((byte) 1, "正常"),
  /**
   * 终止
   */
  TERMINATION((byte) 2, "终止"),
  /**
   * 锁定
   */
  LOCKED((byte) 3, "锁定"),
  /**
   * 理货待上架
   */
  TALLY_WAITING((byte) 4, "理货待上架"),
  /**
   * 调拨在途
   */
  ALLOCATE_ROUTE((byte) 5, "调拨在途"),
  /**
   * 未质检
   */
  UNCHECKED((byte) 6, "未质检"),
  /**
   * 零库存
   */
  ZERO_INVENTORY((byte) 7, "零库存"),
  /**
   * 不予结算
   */
  NOT_SETTLED((byte) 8, "不予结算"),
  /**
   * 溢出
   */
  SPILLOVER((byte) 9, "溢出"),
  /**
   * 不合格
   */
  UNQUALIFIED((byte) 10, "不合格"),
  /**
   * 待处理
   */
  PENDING_PROCESSING((byte) 11, "待处理"),
  ;

  private final Byte id;
  private final String name;
}

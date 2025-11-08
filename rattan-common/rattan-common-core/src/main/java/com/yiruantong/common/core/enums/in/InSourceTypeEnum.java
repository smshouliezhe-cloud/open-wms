package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 来源类型枚举
 */
@Getter
@AllArgsConstructor
public enum InSourceTypeEnum {
  /**
   * 入库计划单
   */
  IN_ORDER_PLAN((byte) 1, "入库计划单"),
  /**
   * 无单入库
   */
  NO_BILL((byte) 2, "无单入库"),
  /**
   * 出库订单
   */
  OUT_ORDER((byte) 3, "出库订单"),
  /**
   * 一键闪入
   */
  NO_BILL_FLASH((byte) 4, "一键闪入"),
  /**
   * 自产材
   */
  IN_ZCC((byte) 5, "自产材"),
  ;

  private final Byte id;
  private final String name;
}

package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库计划状态枚举
 */
@Getter
@AllArgsConstructor
public enum InOrderPlanTypeEnum {
  /**
   * 普通订单
   */
  ORDINARY_ORDER((byte) 1, "普通订单"),
  /**
   * 加急订单
   */
  URGENT_ORDER((byte) 2, "加急订单");

  private final Byte id;
  private final String name;
}

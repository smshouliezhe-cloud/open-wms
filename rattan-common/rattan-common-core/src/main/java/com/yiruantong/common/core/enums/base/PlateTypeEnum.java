package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 托盘类型枚举
 */
@Getter
@AllArgsConstructor
public enum PlateTypeEnum {
  /**
   * 周转箱
   */
  TURNOVER_BOX((byte) 0, "周转箱"),
  /**
   * 暂存箱
   */
  STAGING_BOX((byte) 1, "暂存箱"),
  /**
   * 托盘
   */
  PLATE((byte) 2, "托盘");

  private final Byte id;
  private final String name;
}

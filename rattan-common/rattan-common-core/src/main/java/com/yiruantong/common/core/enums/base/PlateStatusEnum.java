package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 托盘状态枚举
 */
@Getter
@AllArgsConstructor
public enum PlateStatusEnum {
  /**
   * 未使用
   */
  NOT_USED((byte) 0, "未使用"),
  /**
   * 已使用
   */
  USED((byte) 1, "已使用"),

  /**
   * 已核对
   */
  ISCHECK((byte) 2, "已核对");

  private final Byte id;
  private final String name;
}

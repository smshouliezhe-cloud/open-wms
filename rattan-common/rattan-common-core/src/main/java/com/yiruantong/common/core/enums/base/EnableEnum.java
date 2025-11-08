package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否可用
 */
@Getter
@AllArgsConstructor
public enum EnableEnum {
  /**
   * 可用
   */
  ENABLE((byte) 1, "可用"),
  /**
   * 不可用
   */
  DISABLE((byte) 0, "不可用");

  private final Byte id;
  private final String name;
}

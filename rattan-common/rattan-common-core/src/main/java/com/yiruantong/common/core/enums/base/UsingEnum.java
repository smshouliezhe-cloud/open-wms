package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 使用状态
 */
@Getter
@AllArgsConstructor
public enum UsingEnum {
  /**
   * 未使用
   */
  NO_USE((byte) 0, "未使用"),
  /**
   * 使用中
   */
  USING((byte) 1, "使用中");
  private final Byte id;
  private final String name;
}

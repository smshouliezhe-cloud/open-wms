package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别
 */
@Getter
@AllArgsConstructor
public enum SexEnum {
  /**
   * 男
   */
  MALE((byte) 0, "男"),
  /**
   * 女
   */
  FEMALE((byte) 1, "女");
  private final Byte id;
  private final String name;
}

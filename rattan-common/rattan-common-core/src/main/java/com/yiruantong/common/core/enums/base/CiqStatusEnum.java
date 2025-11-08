package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 海关状态枚举
 */
@Getter
@AllArgsConstructor
public enum CiqStatusEnum {
  /**
   * 检查
   */
  CHECK((byte) 1, "检查"),
  /**
   * 放行
   */
  RELEASE((byte) 2, "放行"),
  /**
   * 扣留
   */
  DETAIN((byte) 3, "扣留");

  private final Byte id;
  private final String name;
}

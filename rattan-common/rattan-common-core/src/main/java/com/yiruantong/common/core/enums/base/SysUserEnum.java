package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 使用状态
 */
@Getter
@AllArgsConstructor
public enum SysUserEnum {
  /**
   * 未审核
   */
  AUDIT((byte) 0, "未审核"),
  /**
   * 启用
   */
  OPEN((byte) 1, "启用"),
  /**
   * 停止
   */
  STOP((byte) 2, "停止");
  private final Byte id;
  private final String name;
}

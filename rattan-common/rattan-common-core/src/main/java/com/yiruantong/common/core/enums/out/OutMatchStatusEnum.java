package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单配货状态枚举
 */
@Getter
@AllArgsConstructor
public enum OutMatchStatusEnum {
  /**
   * 未配货
   */
  WAITING((byte) 0, "未配货"),
  /**
   * 配货中
   */
  MATCHING((byte) 1, "配货中"),
  /**
   * 部分配货
   */
  MATCHING_PARTIAL((byte) 2, "部分配货"),
  /**
   * 已完成
   */
  FINISHED((byte) 3, "配货完成");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutMatchStatusEnum matchingEnum(String name) {
    for (OutMatchStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

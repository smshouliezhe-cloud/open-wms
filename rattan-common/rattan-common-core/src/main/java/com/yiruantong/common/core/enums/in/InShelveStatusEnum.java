package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上架单状态枚举
 */
@Getter
@AllArgsConstructor
public enum InShelveStatusEnum {
  /**
   * 待上架
   */
  WAITING((byte) 0, "待上架"),
  /**
   * 上架中
   */
  SHELVING((byte) 1, "上架中"),
  /**
   * 部分上架
   */
  PARTIAL_FINISHED((byte) 2, "部分上架"),
  /**
   * 上架完成
   */
  FINISHED((byte) 3, "上架完成"),
  /**
   * 强制完成
   */
  COMPLETION((byte) 4, "强制完成");

  private final Byte id;
  private final String name;
}

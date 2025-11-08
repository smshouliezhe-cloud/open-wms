package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预到货单退货状态枚举
 */
@Getter
@AllArgsConstructor
public enum InOrderReturnStatusEnum {
  /**
   * 无状态
   */
  NONE((byte) 1, null),
  /**
   * 已退货
   */
  RETURNED((byte) 2, "已退货"),

  /**
   * 部分退货
   */
  RETURNED_PARTIAL((byte) 3, "部分退货");

  private final Byte id;
  private final String name;
}

package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 质检类型枚举
 */
@Getter
@AllArgsConstructor
public enum QualityCheckTypeEnum {
  /**
   * 收货前质检验证
   */
  INBOUND_BEFORE((byte) 0, "收货前质检验证"),
  /**
   * 收货后上架前质检验证
   */
  INBOUND_AFTER((byte) 1, "收货后上架前质检验证");

  private final Byte id;
  private final String name;
}

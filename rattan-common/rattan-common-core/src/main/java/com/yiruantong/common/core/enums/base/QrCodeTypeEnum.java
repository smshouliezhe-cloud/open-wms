package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 二维码类型
 */
@Getter
@AllArgsConstructor
public enum QrCodeTypeEnum {
  /**
   * 出库单
   */
  OUT_ORDER(1, "出库单"),
  /**
   * 预到货单
   */
  IN_ORDER(2, "预到货单"),
  ;

  private final int id;
  private final String name;
}

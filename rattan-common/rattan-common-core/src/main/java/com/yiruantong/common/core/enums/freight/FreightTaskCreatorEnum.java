package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务执行方
 */
@Getter
@AllArgsConstructor
public enum FreightTaskCreatorEnum {
  /**
   * 我方
   */
  MY((byte) 1, "我方"),
  /**
   * 货主
   */
  CONSIGNOR((byte) 2, "货主"),
  ;
  private final Byte id;
  private final String name;
}

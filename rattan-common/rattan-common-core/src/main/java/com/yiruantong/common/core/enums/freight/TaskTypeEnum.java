package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TaskTypeEnum {
  /**
   * 订舱
   */
  ORDERBOOKCABIN((byte) 1, "订舱"),
  /**
   * 待审核
   */
  FINISHED((byte) 2, "已完成"),
  ;
  private final Byte id;
  private final String name;
}

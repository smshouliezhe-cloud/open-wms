package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FreightTaskStateEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "未完成"),
  /**
   * 待审核
   */
  FINISHED((byte) 2, "已完成"),
  ;
  private final Byte id;
  private final String name;
}

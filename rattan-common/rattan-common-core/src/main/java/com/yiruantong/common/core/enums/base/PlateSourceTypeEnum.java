package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlateSourceTypeEnum {
  /**
   * 容器借出
   */
  PLATE_OUT((byte) 0, "容器借出"),
  /**
   * 容器归还
   */
  PLATE_IN((byte) 1, "容器归还"),
  /**
   * 垫板入库
   */
  BACKPLATE((byte) 2, "垫板入库");

  private final Byte id;
  private final String name;

}

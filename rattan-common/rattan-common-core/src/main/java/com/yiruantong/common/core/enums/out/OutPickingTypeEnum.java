package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单拣货类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutPickingTypeEnum {
  /**
   * PC常规下架
   */
  PC_ORDER_PICKING((byte) 0, "PC常规下架"),
  /**
   * PC按拍下架
   */
  PC_WAVE_DOWN((byte) 1, "PC按拍下架"),
  /**
   * PDA常规下架
   */
  PDA_ORDER_PICKING((byte) 2, "PDA常规下架"),
  /**
   * PDA按拍下架
   */
  PDA_PLATE_PICKING((byte) 3, "PDA按拍下架"),
  /**
   * PDA摘果下架
   */
  PDA_ORDER_PICKING_ZG((byte) 4, "PDA摘果下架"),
  ;


  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutPickingTypeEnum matchingEnum(String name) {
    for (OutPickingTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

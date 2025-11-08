package com.yiruantong.inventory.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HistoryTypeEnum {
  /**
   * 增加库存轨迹
   */
  IN((byte) 1, "增加库存轨迹"),
  /**
   * 出库轨迹
   */
  OUT((byte) 2, "减少库存轨迹");

  private final Byte id;
  private final String name;
}

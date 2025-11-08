package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FreightTypeEnum {
  /**
   * 常温运价
   */
  ROOM_TEMPERATURE_FREIGHT_RATE((byte) 1, "常温运价"),
  /**
   * 低温运价
   */
  LOW_TEMPERATURE_FREIGHT_RATE((byte) 2, "低温运价"),
  ;
  private final Byte id;
  private final String name;
}

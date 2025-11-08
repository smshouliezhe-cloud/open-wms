package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddressTypeEnum {
  /**
   * 收货人
   */
  CONSIGNEE((byte) 1, "收货人"),
  /**
   * 发货人
   */
  SHIPPER((byte) 2, "发货人"),
  /**
   * 海外代理
   */
  OVERSEA_AGENT((byte) 3, "海外代理"),
  /**
   * 通知人
   */
  NOTIFIER((byte) 4, "通知人"),
  ;
  private final Byte id;
  private final String name;
}

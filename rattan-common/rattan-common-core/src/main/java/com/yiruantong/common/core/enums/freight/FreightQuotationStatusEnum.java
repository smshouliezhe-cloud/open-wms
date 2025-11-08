package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货代询价订单状态
 */
@Getter
@AllArgsConstructor
public enum FreightQuotationStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 询价中
   */
  IN_PROGRESS((byte) 2, "询价中"),
  /**
   * 已生成报价单
   */
  QUOTED((byte) 3, "已生成报价单"),
  ;
  private final Byte id;
  private final String name;
}

package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 报价单状态
 */
@Getter
@AllArgsConstructor
public enum FreightQuotedStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 报价中
   */
  IN_PROGRESS((byte) 2, "报价中"),
  /**
   * 已生成订单
   */
  CREATE_ORDER((byte) 3, "已生成订单"),
  ;
  private final Byte id;
  private final String name;
}

package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceInvoiceActionEnum {
  /**
   * 订单操作
   */
  ORDER_OPERATION((byte) 1, "订单操作");
  private final Byte id;
  private final String name;
}

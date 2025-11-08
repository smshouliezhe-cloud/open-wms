package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancePayableActionEnum {
  /**
   * 采购付款
   */
  PURCHASE_PAYMENT((byte) 1, "采购付款"),
  /**
   * 订单操作
   */
  ORDER_OPERATION((byte) 2, "订单操作"),
  /**
   * 付款核销
   */
  PAY_CANCEL((byte) 3, "付款核销"),
  /**
   * 付款退回
   */
  PAY_RETURN((byte) 4, "付款退回");
  private final Byte id;
  private final String name;
}

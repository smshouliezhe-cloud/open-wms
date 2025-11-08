package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceReceivableActionEnum {
  /**
   * 生成对账单
   */
  ADD_ACCOUNTS_ORDER((byte) 1, "生成对账单"),
  /**
   * 订单操作
   */
  ORDER_OPERATION((byte) 2, "订单操作"),
  /**
   * 开票申请
   */
  INVOICE_APPLICATION((byte) 3, "开票申请"),
  /**
   * 账单核销
   */
  BILL_CANCEL((byte) 4, "账单核销"),
  /**
   * 反核销
   */
  COUNTER_BILL_CANCEL((byte) 5, "反核销");
  private final Byte id;
  private final String name;
}

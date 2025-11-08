package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceReceiveActionEnum {
  /**
   * 单据操作
   */
  ORDER_OPERATION((byte) 1, "新建收款"),
  /**
   * 收据审核
   */
  AUDITING((byte) 2, "收据审核"),
  /**
   * 终止
   */
  STOP((byte) 3, "单据终止"),
  /**
   * 开启
   */
  OPEN((byte) 4, "单据开启"),
  /**
   * 强制完成
   */
  FORCE_ACCOMPLISH((byte) 5, "强制完成"),
  /**
   * 收款核销
   */
  WRITE_OFF((byte) 6, "收款核销"),
  /**
   * 账单核销
   */
  BILL_WRITE_OFF((byte) 8, "账单核销"),
  /**
   * 反核销
   */
  COUNTER_BILL_CANCEL((byte) 9, "反核销");;
  private final Byte id;
  private final String name;
}

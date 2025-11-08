package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceInvoiceStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 待审核
   */
  PENDING((byte) 2, "待审核"),
  /**
   * 审核成功
   */
  AUDITED_SUCCESS((byte) 3, "审核成功"),
  /**
   * 开启
   */
  OPEN((byte) 4, "开启"),
  /**
   * 终止
   */
  STOP((byte) 5, "终止"),
  /**
   * 已开票
   */
  INVOICE_ALREADY_OPEN((byte) 6, "已开票"),
  /**
   * 未开票
   */
  INVOICE_UNOPENED((byte) 7, "未开票"),
  /**
   * 部分开票
   */
  INVOICE_PART((byte) 8, "部分开票");
  private final Byte id;
  private final String name;
}

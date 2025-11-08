package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancePayoutStatusEnum {
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
   * 未付款
   */
  NOT_PAY((byte) 6, "未付款"),
  /**
   * 部分付款
   */
  PART_PAY((byte) 7, "部分付款"),
  /**
   * 已付款
   */
  ALREADY_PAY((byte) 8, "已付款");
  private final Byte id;
  private final String name;
}

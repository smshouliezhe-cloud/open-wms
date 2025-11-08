package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceReceiveRecordStatusEnum {
  /**
   * 已取消
   */
  CANCELLED((byte) 1, "已取消"),
  /**
   * 已核销
   */
  WRITE_OFF((byte) 2, "已核销");
  private final Byte id;
  private final String name;
}

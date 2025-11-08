package com.yiruantong.common.core.enums.finance;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseFreightStatusEnum {
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
   * 商务锁定
   */
  LUCK((byte) 4, "商务锁定"),
  /**
   * 取消锁定
   */
  UN_LUCK((byte) 5, "取消锁定");
  private final Byte id;
  private final String name;
}

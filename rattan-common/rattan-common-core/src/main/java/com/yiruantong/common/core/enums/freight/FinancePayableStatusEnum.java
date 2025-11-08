package com.yiruantong.common.core.enums.freight;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinancePayableStatusEnum {
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
   * 未支付
   */
  NOT_PAID((byte) 6, "未支付"),
  /**
   * 部分支付
   */
  PARTIAL_PAYMENT((byte) 7, "部分支付"),
  /**
   * 已支付
   */
  ALREADY_PAID((byte) 8, "已支付"),
  /**
   * 部分核销
   */
  PARTIAL_WRITE_OFF((byte) 9, "部分核销"),
  /**
   * 已核销
   */
  WRITE_OFF((byte) 10, "已核销"),
  /**
   * 核销中
   */
  MIDDLE_WRITE_OFF((byte) 11, "核销中"),
  /**
   * 未审核
   */
  NO_AUDITED((byte) 12, "未审核");
  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FinancePayableStatusEnum matchingEnum(String name) {
    for (FinancePayableStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

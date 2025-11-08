package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分拣规则运算符枚举
 */
@Getter
@AllArgsConstructor
public enum SortingRuleOperatorEnum {
  /**
   * 等于
   */
  EQUAL((byte) 1, "equal", "等于"),
  ;

  private final Byte id;
  private final String code;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param code 值
   * @return 枚举
   */
  public static SortingRuleOperatorEnum getEnumByCode(String code) {
    for (SortingRuleOperatorEnum i : values()) {
      if (i.getCode().equals(code)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static SortingRuleOperatorEnum matchingEnum(String name) {
    for (SortingRuleOperatorEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

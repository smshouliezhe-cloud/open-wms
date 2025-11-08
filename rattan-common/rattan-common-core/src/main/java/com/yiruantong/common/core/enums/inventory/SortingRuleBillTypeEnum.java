package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分拣规则类型枚举
 */
@Getter
@AllArgsConstructor
public enum SortingRuleBillTypeEnum {
  /**
   * 出库单
   */
  SALE_ORDER((byte) 1, "出库单"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static SortingRuleBillTypeEnum matchingEnum(String name) {
    for (SortingRuleBillTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

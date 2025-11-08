package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分拣规则类型枚举
 */
@Getter
@AllArgsConstructor
public enum SortingRuleTypeEnum {
  /**
   * 货位
   */
  POSITIONNAME((byte) 1, "positionName", "货位"),
  /**
   * 货位数量
   */
  POSITIONNAMEQTY((byte) 2, "positionNameQty", "货位数量"),
  /**
   * 生产日期
   */
  PRODUCEDATE((byte) 3, "produceDate", "生产日期"),
  /**
   * 生产日期范围
   */
  PRODUCEDATERANGE((byte) 4, "produceDateRange", "生产日期范围"),
  /**
   * 批次号
   */
  BATCHNUMBER((byte) 5, "batchNumber", "批次号"),
  /**
   * 货位数量
   */
  BATCHNUMBERQTY((byte) 2, "batchNumberQty", "批次数量"),
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
  public static SortingRuleTypeEnum getEnumByCode(String code) {
    for (SortingRuleTypeEnum i : values()) {
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
  public static SortingRuleTypeEnum matchingEnum(String name) {
    for (SortingRuleTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

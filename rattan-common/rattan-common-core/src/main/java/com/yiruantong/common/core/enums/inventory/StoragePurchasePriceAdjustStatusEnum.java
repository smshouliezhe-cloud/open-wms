package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 成本单价调整状态枚举
 */
@Getter
@AllArgsConstructor
public enum StoragePurchasePriceAdjustStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 调整完成
   */
  FINISHED((byte) 2, "调整完成");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StoragePurchasePriceAdjustStatusEnum matchingEnum(String name) {
    for (StoragePurchasePriceAdjustStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存下限补货动作枚举
 */
@Getter
@AllArgsConstructor
public enum StorageReplenishmentActionEnum {
  /**
   * 库存下限补货
   */
  STORAGELOWER((byte) 1, "库存下限补货"),
  /**
   * 缺货紧急补货
   */
  LACKSTORAGE_REPLENISHMENT((byte) 2, "缺货紧急补货"),

  /**
   * 终止
   */
  STOP((byte) 3, "终止"),
  /**
   * 开启
   */
  OPEN((byte) 4, "开启");
  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageReplenishmentActionEnum matchingEnum(String name) {
    for (StorageReplenishmentActionEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

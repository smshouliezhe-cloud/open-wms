package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存分拣类型枚举
 */
@Getter
@AllArgsConstructor
public enum InventorySortTypeEnum {
  /**
   * 零散分拣
   */
  SCATTERED_SORTING((byte) 1, "零散分拣"),
  /**
   * 整拣分拣
   */
  FULL_CONTAINER_SORTING((byte) 2, "整拣分拣"),
  /**
   * 整托
   */
  FULL_PLATE_SORTING((byte) 3, "整托分拣");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InventorySortTypeEnum matchingEnum(String name) {
    for (InventorySortTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InventorySortTypeEnum matchingEnumById(Byte name) {
    for (InventorySortTypeEnum i : values()) {
      if (i.getId().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存调整状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageAdjustStatusEnum {
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
  public static StorageAdjustStatusEnum matchingEnum(String name) {
    for (StorageAdjustStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

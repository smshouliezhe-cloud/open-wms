package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他出库状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageCheckTypeEnum {
  /**
   * 新建
   */
  REPLAY((byte) 1, "复盘");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageCheckTypeEnum matchingEnum(String name) {
    for (StorageCheckTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货主过户状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageConsignorStransferStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 过户完成
   */
  FINISHED((byte) 2, "过户完成");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageConsignorStransferStatusEnum matchingEnum(String name) {
    for (StorageConsignorStransferStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存SN调整状态
 */
@Getter
@AllArgsConstructor
public enum StorageSnAdjustStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 已提交
   */
  FINSISHED((byte) 2, "已调整");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageSnAdjustStatusEnum matchingEnum(String name) {
    for (StorageSnAdjustStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

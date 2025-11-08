package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 库存下限补货状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageReplenishmentStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),

  /**
   * 审核成功
   */
  AUDITED_SUCCESS((byte) 2, "审核成功"),
  /**
   * 调整完成
   */
  FINISHED((byte) 3, "调整完成"),
  /**
   * 部分补货
   */
  PARTIAL_REPLENISHMENT((byte) 4, "部分补货"),
  /**
   * 完全补货
   */
  COMPLETE_REPLENISHMENT((byte) 5, "完全补货"),
  ;
  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageReplenishmentStatusEnum matchingEnum(String name) {
    for (StorageReplenishmentStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

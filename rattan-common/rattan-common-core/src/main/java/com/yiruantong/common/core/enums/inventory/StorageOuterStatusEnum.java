package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他出库状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageOuterStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 出库完成
   */
  FINISHED((byte) 2, "出库完成"),
  /**
   * 审核成功
   */
  SUCCESS((byte) 3, "审核成功"),
  /**
   * 其他出库单
   */
  STORAGE_OUTER((byte) 4, "其他出库单"),
  /**
   * 已转出库
   */
  TO_OUT_ORDER((byte) 5, "已转出库");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageOuterStatusEnum matchingEnum(String name) {
    for (StorageOuterStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他入库状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageAllocateApplyActionEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核
   */
  SUCCESS((byte) 2, "审核"),
  /**
   * 终止
   */
  STOP((byte) 3, "终止"),
  /**
   * 开启
   */
  OPEN((byte) 4, "开启"),
  /**
   * 转出库单
   */
  TO_OUT_ORDER((byte) 5, "转出库单"),
  /**
   * PC扫描出库
   */
  PC_SCAN_OUT((byte) 6, "PC扫描出库"),
  /**
   * PC扫描入库
   */
  PC_SCAN_IN((byte) 6, "PC扫描入库");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageAllocateApplyActionEnum matchingEnum(String name) {
    for (StorageAllocateApplyActionEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

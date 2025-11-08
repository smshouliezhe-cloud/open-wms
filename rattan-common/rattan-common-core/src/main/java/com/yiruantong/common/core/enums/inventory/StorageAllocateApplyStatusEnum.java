package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他入库状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageAllocateApplyStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核成功
   */
  SUCCESS((byte) 2, "审核成功"),
  /**
   * 审核失败
   */
  FAILED((byte) 3, "审核失败"),
  /**
   * 终止
   */
  STOP((byte) 4, "终止"),
  /**
   * 已转出库
   */
  TO_OUT_ORDER((byte) 5, "已转出库"),
  /**
   * 部分出库
   */
  PARTIAL_TO_OUT((byte) 6, "部分出库"),
  /**
   * 出库完成
   */
  OUT_FINISHED((byte) 7, "出库完成"),
  /**
   * 部分入库
   */
  PARTIAL_TO_IN((byte) 8, "部分入库"),
  /**
   * 完全入库
   */
  IN_FINISHED((byte) 9, "完全入库"),
  /**
   * 调拨完成
   */
  ALLOCATE_FINISHED((byte) 10, "调拨完成"),
  /**
   * 常规调拨出库
   */
  ROUTINE_ALLOCATE_OUT((byte) 10, "常规调拨出库"),
  /**
   * 常规调拨入库
   */
  ROUTINE_ALLOCATE_IN((byte) 10, "常规调拨入库"),
  /**
   * 调拨在途虚拟入库
   */
  ALLOCATE_ROUTE_VIRTUAL_IN((byte) 11, "调拨在途虚拟入库"),
  /**
   * 调拨在途虚拟入库
   */
  ALLOCATE_ROUTE_VIRTUAL_OUT((byte) 11, "调拨在途虚拟出库");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageAllocateApplyStatusEnum matchingEnum(String name) {
    for (StorageAllocateApplyStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

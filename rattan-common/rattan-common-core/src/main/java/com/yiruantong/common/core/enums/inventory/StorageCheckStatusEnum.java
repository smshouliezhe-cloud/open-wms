package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他出库状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageCheckStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 已提交
   */
  SUBIMT((byte) 2, "已提交"),
  /**
   * 已生成盈亏单
   */
  CREATEPROFITLOSS((byte) 3, "已生成盈亏单"),
  /**
   * 复盘中
   */
  INREVIEW((byte) 4, "复盘中");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageCheckStatusEnum matchingEnum(String name) {
    for (StorageCheckStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

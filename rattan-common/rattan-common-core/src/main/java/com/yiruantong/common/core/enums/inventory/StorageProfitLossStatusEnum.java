package com.yiruantong.common.core.enums.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他出库状态枚举
 */
@Getter
@AllArgsConstructor
public enum StorageProfitLossStatusEnum {
  /**
   * 新建
   */
  NEWED((byte) 0, "新建"),
  /**
   * 已复盘
   */
  REVIEWED((byte) 1, "已复盘"),
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
  REVIEWED_CENTER((byte) 4, "复盘中"),
  /**
   * 已调整
   */
  ADJUSTED((byte) 5, "已调整");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static StorageProfitLossStatusEnum matchingEnum(String name) {
    for (StorageProfitLossStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

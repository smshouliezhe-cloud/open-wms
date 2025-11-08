package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单打包状态枚举
 */
@Getter
@AllArgsConstructor
public enum OutPackageStatusEnum {
  /**
   * 未打包
   */
  WAITING((byte) 0, "未打包"),
  /**
   * 打包中
   */
  PACKAGING((byte) 1, "打包中"),
  /**
   * 部分打包
   */
  PACKAGING_PARTIAL((byte) 2, "部分打包"),
  /**
   * 打包完成
   */
  FINISHED((byte) 3, "打包完成");


  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutPackageStatusEnum matchingEnum(String name) {
    for (OutPackageStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

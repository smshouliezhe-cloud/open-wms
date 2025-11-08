package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单操作类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutOrderWaveStatusEnum {
  /**
   * 波次完成
   */
  WAVE_FINISHED((byte) 1, "波次完成"),
  /**
   * 待拣货
   */
  WAIT_PICKING((byte) 2, "待拣货"),

  /**
   * 拣货中
   */
  PICKING((byte) 3, "拣货中"),

  /**
   * 部分拣货
   */
  PART_PICKING((byte) 4, "部分拣货"),
  /**
   * 拣货完成
   */
  PICKED((byte) 5, "拣货完成"),
  /**
   * 配货完成
   */
  MATCHED((byte) 6, "配货完成"),
  /**
   * 部分打包
   */
  PACKAGE_PARTIAL((byte) 7, "部分打包"),
  /**
   * 打包完成
   */
  PACKAGE_FINISHED((byte) 8, "打包完成");


  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutOrderWaveStatusEnum matchingEnum(String name) {
    for (OutOrderWaveStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

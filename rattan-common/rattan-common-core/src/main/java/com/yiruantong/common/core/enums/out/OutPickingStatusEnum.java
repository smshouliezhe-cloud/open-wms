package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单拣货状态枚举
 */
@Getter
@AllArgsConstructor
public enum OutPickingStatusEnum {
  /**
   * 未拣货
   */
  WAITING((byte) 0, "未拣货"),
  /**
   * 拣货中
   */
  PICKING((byte) 1, "拣货中"),
  /**
   * 部分拣货
   */
  PICKING_PARTIAL((byte) 2, "部分拣货"),
  /**
   * 已完成
   */
  PICKED((byte) 3, "拣货完成");


  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutPickingStatusEnum matchingEnum(String name) {
    for (OutPickingStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

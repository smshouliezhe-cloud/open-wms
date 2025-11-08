package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库计划状态枚举
 */
@Getter
@AllArgsConstructor
public enum OutOrderPlanStatusEnum {
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
   * 待审核
   */
  PENDING((byte) 4, "待审核"),
  /**
   * 通过审核
   */
  PASS_AUDIT((byte) 5, "通过审核"),
  /**
   * 终止
   */
  STOP((byte) 6, "终止"),
  /**
   * 开启
   */
  OPEN((byte) 7, "开启"),
  /**
   * 部分出库
   */
  PARTIAL_TO_OUT((byte) 8, "部分出库"),
  /**
   * 出库完成
   */
  OUT_FINISHED((byte) 9, "已出库"),
  /**
   * 已转出库单
   */
  OVER_TO_OUTORDER((byte) 10, "已转出库单");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutOrderPlanStatusEnum matchingEnum(String name) {
    for (OutOrderPlanStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 车辆管理状态
 */
@Getter
@AllArgsConstructor
public enum VehicleStatusEnum {
  /**
   * 待排班
   */
  WAIT_SCHEDUL((byte) 1, "待排班"),
  /**
   * 任务中
   */
  IN_TASK((byte) 2, "任务中"),
  /**
   * 可使用
   */
  ENABLE((byte) 3, "可使用");

  private final Byte id;
  private final String name;
}

package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否可用
 */
@Getter
@AllArgsConstructor
public enum TaskQueueStatusEnum {
  /**
   *
   */
  AWAITING_IMPLEMENTATION((byte) 1, "待执行"),
  /**
   * 执行完成
   */
  IMPLEMENTATION_COMPLETED((byte) 2, "执行完成"),
  /**
   * 执行失败
   */
  IMPLEMENTATION_FAIL((byte) 3, "执行失败");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static TaskQueueStatusEnum matchingEnum(String name) {
    for (TaskQueueStatusEnum i : values()) {
      if (ObjectUtil.equal(i.getName(), name)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param id 值
   * @return 枚举
   */
  public static TaskQueueStatusEnum matchingEnumById(int id) {
    for (TaskQueueStatusEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

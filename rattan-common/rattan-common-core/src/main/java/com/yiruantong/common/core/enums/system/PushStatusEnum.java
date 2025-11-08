package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WCS任务类型
 */
@Getter
@AllArgsConstructor
public enum PushStatusEnum {
  /**
   * 待推送
   */
  WAIT((byte) 1, "待推送"),
  /**
   * 推送成功
   */
  SUCCESS((byte) 2, "推送成功"),
  /**
   * 推送失败
   */
  FAILED((byte) 3, "推送失败"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static PushStatusEnum matchingEnum(String name) {
    for (PushStatusEnum i : values()) {
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
  public static PushStatusEnum matchingEnumById(int id) {
    for (PushStatusEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

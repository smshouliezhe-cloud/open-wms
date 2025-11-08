package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否可用
 */
@Getter
@AllArgsConstructor
public enum RoleTypeEnum {
  /**
   * 功能模块
   */
  MODULE((byte) 1, "功能模块"),
  /**
   * PDA
   */
  PDA((byte) 2, "PDA"),
  /**
   * 微信小程序
   */
  WECHAT((byte) 3, "微信小程序"),
  /**
   * APP功能模块
   */
  APP_MODULE((byte) 4, "APP功能模块");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static RoleTypeEnum matchingEnum(String name) {
    for (RoleTypeEnum i : values()) {
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
  public static RoleTypeEnum matchingEnumById(Byte id) {
    for (RoleTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

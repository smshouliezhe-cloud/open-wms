package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分拣池状态
 */
@Getter
@AllArgsConstructor
public enum PoolStateEnum {
  /**
   * 睡眠
   */
  SLEEP((byte) 1, "睡眠"),
  /**
   * 激活
   */
  ACTIVE((byte) 2, "激活");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static PoolStateEnum matchingEnum(String name) {
    for (PoolStateEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

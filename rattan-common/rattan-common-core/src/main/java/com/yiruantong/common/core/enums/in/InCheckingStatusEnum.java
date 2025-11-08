package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预到货单质检状态枚举
 */
@Getter
@AllArgsConstructor
public enum InCheckingStatusEnum {
  /**
   * 未质检
   */
  UNCHECKED((byte) 1, "未质检"),
  /**
   * 确认入库
   */
  CHECKED((byte) 2, "质检完成"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InCheckingStatusEnum matchingEnum(String name) {
    for (InCheckingStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

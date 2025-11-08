package com.yiruantong.common.core.enums.base;

public interface BaseEnum {
  /**
   * 根据枚举值和type获取枚举
   */
  public static <T extends BaseEnum> T getEnum(Class<T> type, String code) {
    T[] objs = type.getEnumConstants();
    for (T em : objs) {
      if (em.getCode().equals(code)) {
        return em;
      }
    }
    return null;
  }

  String getCode();
}

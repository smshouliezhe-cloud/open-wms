package com.yiruantong.common.core.enums.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 账套申请类型类型枚举
 */
@Getter
@AllArgsConstructor
public enum ClientKeyEnum {
  /**
   * PC
   */
  PC((byte) 1, "pc"),
  /**
   * APP
   */
  APP((byte) 2, "app"),
  /**
   * API
   */
  API((byte) 3, "api"),
  /**
   * Consignor
   */
  Consignor((byte) 4, "consignor"),
  /**
   * 仅安装应用
   */
  DRIVER((byte) 5, "driver");

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param enumName 枚举名
   * @return 枚举
   */
  public static ClientKeyEnum matchingEnum(String enumName) {
    for (ClientKeyEnum i : values()) {
      if (StringUtils.equalsIgnoreCase(i.toString(), enumName)) {
        return i;
      }
    }
    return null;
  }

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static ClientKeyEnum matchingEnumById(Byte name) {
    for (ClientKeyEnum i : values()) {
      if (i.getId().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

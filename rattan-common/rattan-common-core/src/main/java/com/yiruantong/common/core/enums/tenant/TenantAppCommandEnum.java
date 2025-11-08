package com.yiruantong.common.core.enums.tenant;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账套申请类型类型枚举
 */
@Getter
@AllArgsConstructor
public enum TenantAppCommandEnum {
  /**
   * 安装应用及数据
   */
  ALL((byte) 1, "all"),
  /**
   * 仅安装应用
   */
  ONLY_APP((byte) 2, "onlyApp");

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param enumName 枚举名
   * @return 枚举
   */
  public static TenantAppCommandEnum matchingEnum(String enumName) {
    for (TenantAppCommandEnum i : values()) {
      if (ObjectUtil.equal(i.toString(), enumName)) {
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
  public static TenantAppCommandEnum matchingEnumById(Byte name) {
    for (TenantAppCommandEnum i : values()) {
      if (i.getId().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

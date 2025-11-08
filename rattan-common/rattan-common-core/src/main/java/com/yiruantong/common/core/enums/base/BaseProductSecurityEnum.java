package com.yiruantong.common.core.enums.base;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 货位类型枚举
 */
@Getter
@AllArgsConstructor
public enum BaseProductSecurityEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 已作废
   */
  VOIDED((byte) 1, "已作废"),
  /**
   * 已分配
   */
  ASSIGNED((byte) 2, "已分配")

  ;

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param enumName 枚举名
   * @return 枚举
   */
  public static BaseProductSecurityEnum matchingEnum(String enumName) {
    for (BaseProductSecurityEnum i : values()) {
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
  public static BaseProductSecurityEnum matchingEnumById(Byte name) {
    for (BaseProductSecurityEnum i : values()) {
      if (i.getId().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

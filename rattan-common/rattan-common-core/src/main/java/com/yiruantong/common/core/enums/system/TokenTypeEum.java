package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 临时登录token类型
 */
@Getter
@AllArgsConstructor
public enum TokenTypeEum {
  /**
   * 单据扫描
   */
  BILL_SCAN((byte) 1, "单据扫描"),
  /**
   * 流程审核
   */
  FLOWABLE((byte) 2, "流程审核"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static TokenTypeEum matchingEnum(String name) {
    for (TokenTypeEum i : values()) {
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
  public static TokenTypeEum matchingEnumById(Byte id) {
    for (TokenTypeEum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.base;

import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.common.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 月台类型
 */
@Getter
@AllArgsConstructor
public enum BasePlatformTypeEnum {
  /**
   * 正常
   */
  NORMAL((byte) 1, "正常"),
  /**
   * 装车
   */
  LOAD((byte) 2, "装车"),
  /**
   * 卸车
   */
  UNLOAD((byte) 2, "卸车"),
  ;

  private final Byte id;
  private final String name;


  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static BasePlatformTypeEnum matchingEnum(String name) {
    for (BasePlatformTypeEnum i : values()) {
      if (StringUtils.equals(i.getName(), name)) {
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
  public static BasePlatformTypeEnum matchingEnumById(Byte name) {
    for (BasePlatformTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), name)) {
        return i;
      }
    }
    return null;
  }

}

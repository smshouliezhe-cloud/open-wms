package com.yiruantong.common.core.enums.base;

import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.common.core.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 月台状态
 */
@Getter
@AllArgsConstructor
public enum BasePlatformStatusEnum {
  /**
   * 待排班
   */
  WAIT_SCHEDULE((byte) 1, "待排班"),
  /**
   * 已排班
   */
  IN_TASK((byte) 2, "已排班"),
  ;

  private final Byte id;
  private final String name;


  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static BasePlatformStatusEnum matchingEnum(String name) {
    for (BasePlatformStatusEnum i : values()) {
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
  public static BasePlatformStatusEnum matchingEnumById(Byte name) {
    for (BasePlatformStatusEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), name)) {
        return i;
      }
    }
    return null;
  }

}

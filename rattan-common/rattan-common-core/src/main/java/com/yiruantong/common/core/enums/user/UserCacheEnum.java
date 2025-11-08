package com.yiruantong.common.core.enums.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 用户缓存数据
 *
 * @author YiRuanTong
 */
@Getter
@AllArgsConstructor
public enum UserCacheEnum {

  /**
   * 打包出库 cache key
   */
  SCAN_OUT_SETTING(1, "SCAN_OUT_SETTING"),

  /**
   * 补货扫描 cache key
   */
  SCAN_REPLENISHMENT(2, "SCAN_REPLENISHMENT"),
  ;

  private final int id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static UserCacheEnum matchingEnum(String name) {
    for (UserCacheEnum i : values()) {
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
  public static UserCacheEnum matchingEnumById(int name) {
    for (UserCacheEnum i : values()) {
      if (i.getId() == name) {
        return i;
      }
    }
    return null;
  }
}

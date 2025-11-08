package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 用户数据权限枚举
 */
@Getter
@AllArgsConstructor
public enum UserAuthModuleTypeEnum {
  /**
   * 货主模块
   */
  CONSIGNOR(1L, "货主模块"),
  /**
   * 仓库模块
   */
  STORAGE(2L, "仓库模块"),
  /**
   * 供应商模块
   */
  PROVIDER(3L, "供应商模块"),
  /**
   * 功能模块
   */
  MENU(4L, "功能模块"),
  /**
   * 生产班组
   */
  MES_TEAM(5L, "生产班组"),
  ;

  private final Long id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static UserAuthModuleTypeEnum matchingEnum(String name) {
    for (UserAuthModuleTypeEnum i : values()) {
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
  public static UserAuthModuleTypeEnum matchingEnumById(Byte name) {
    for (UserAuthModuleTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), name)) {
        return i;
      }
    }
    return null;
  }
}

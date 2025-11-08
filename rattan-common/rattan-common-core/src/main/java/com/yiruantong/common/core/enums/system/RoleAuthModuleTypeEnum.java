package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 角色权限枚举
 */
@Getter
@AllArgsConstructor
public enum RoleAuthModuleTypeEnum {
  /**
   * 用户模块
   */
  USER_MODULE((byte) 1, "用户模块"),
  /**
   * 岗位模块
   */
  POST_MODULE((byte) 2, "岗位模块");

  private final Byte id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static RoleAuthModuleTypeEnum matchingEnum(String name) {
    for (RoleAuthModuleTypeEnum i : values()) {
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
  public static RoleAuthModuleTypeEnum matchingEnumById(Byte name) {
    for (RoleAuthModuleTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), name)) {
        return i;
      }
    }
    return null;
  }
}

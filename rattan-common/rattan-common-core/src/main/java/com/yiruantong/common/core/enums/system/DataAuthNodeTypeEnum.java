package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.yiruantong.common.core.utils.StringUtils;

/**
 * 数据权限节点枚举
 */
@Getter
@AllArgsConstructor
public enum DataAuthNodeTypeEnum {
  /**
   * 用户类型
   */
  USER(1L, "user"),
  /**
   * 角色类型
   */
  ROLE(2L, "role"),
  /**
   * 司机
   */
  DRIVER(3L, "driver"),
  ;

  private final Long id;
  private final String name;

  /**
   * 匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static DataAuthNodeTypeEnum matchingEnum(String name) {
    for (DataAuthNodeTypeEnum i : values()) {
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
  public static DataAuthNodeTypeEnum matchingEnumById(Byte name) {
    for (DataAuthNodeTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限枚举
 */
@Getter
@AllArgsConstructor
public enum RoleAuthDataEnum {
  /**
   * 货主权限
   */
  CONSIGNOR((byte) 1, "货主权限"),
  /**
   * 仓库权限
   */
  STORAGE((byte) 2, "仓库权限"),
  /**
   * 供应商权限
   */
  PROVIDER((byte) 3, "供应商权限"),
  /**
   * 供应商权限
   */
  APP_MENU((byte) 4, "APP菜单"),
  /**
   * 供应商权限
   */
  TEAM((byte) 5, "生产班组权限"),
  /**
   * 商品类别权限
   */
  TYPE((byte) 6, "商品类别权限"),
  /**
   * 承运商
   */
  CARRIER((byte) 7, "承运商"),
  /**
   * 车辆权限
   */
  VEHICLE((byte) 8, "车辆权限"),
  /**
   * 司机权限
   */
  DRIVER((byte) 9, "司机权限");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static RoleAuthDataEnum matchingEnum(String name) {
    for (RoleAuthDataEnum i : values()) {
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
  public static RoleAuthDataEnum matchingEnumById(Byte id) {
    for (RoleAuthDataEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

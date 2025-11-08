package com.yiruantong.common.core.enums.other;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * serviceId类型枚举
 */
@Getter
@AllArgsConstructor
public enum XgOrderTypeEnum {
  /**
   * 材料/备件
   */
  MATERIAL_SPARE_PARTS((byte) 1, "材料/备件"),
  /**
   * 合金/辅料
   */
  ALLOY_AUXILIARY_MATERIAL((byte) 2, "合金/辅料"),
  /**
   * 自产材
   */
  IN_HOUSE_MATERIAL((byte) 3, "自产材"),
  /**
   * 紧急放行单
   */
  EMERGENCY_RELEASE_BILL((byte) 4, "紧急放行单"),
  /**
   * 退货出库
   */
  RETURN_OUTBOUND((byte) 5, "退货出库"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static XgOrderTypeEnum matchingEnum(String name) {
    for (XgOrderTypeEnum i : values()) {
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
  public static XgOrderTypeEnum matchingEnumById(Byte id) {
    for (XgOrderTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.fee;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 物流方式枚举
 */
@Getter
@AllArgsConstructor
public enum FeeLogisticsEnum {
  /**
   * 海运
   */
  SEA_TRANSPORTATION((byte) 1, "海运"),
  /**
   * 铁路
   */
  RAILWAY((byte) 2, "铁路"),
  /**
   * 卡航
   */
  KAHANG((byte) 3, "卡航"),
  /**
   * 陆运
   */
  LAND_TRANSPORTATION((byte) 4, "陆运"),
  /**
   * 空运
   */
  AIR_TRANSPORT((byte) 5, "空运"),
  /**
   * 国际快递
   */
  DHL((byte) 6, "国际快递"),
  /**
   * 国内快递（省外）
   */
  DOMESTIC_EXPRESS_OUT((byte) 7, "国内快递（省外）"),
  /**
   * 国内快递（省内）
   */
  DOMESTIC_EXPRESS_IN((byte) 8, "国内快递（省内）"),
  /**
   * 按车辆
   */
  VEHICLE((byte) 9, "按车辆"),
  /**
   * 首重+区间
   */
  FIRST_WEIGHT_INTERVAL((byte) 10, "首重+区间");
  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FeeLogisticsEnum matchingEnum(String name) {
    for (FeeLogisticsEnum i : values()) {
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
  public static FeeLogisticsEnum matchingEnumById(int id) {
    for (FeeLogisticsEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

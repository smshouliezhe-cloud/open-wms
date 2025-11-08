package com.yiruantong.common.core.enums.fee;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收费方式
 */
@Getter
@AllArgsConstructor
public enum FeeChargeEnum {
  /**
   * 按重量
   */
  WEIGHT((byte) 1, "weight", "按重量"),
  /**
   * 按件/箱
   */
  BOX((byte) 2, "unit", "按件/箱"),
  /**
   * 按平米
   */
  AREA((byte) 3, "area", "按平米"),
  /**
   * 按体积
   */
  CUBE((byte) 4, "cube", "按体积"),
  /**
   * 按数量
   */
  NUMBER((byte) 5, "number", "按数量"),
  /**
   * 按单
   */
  ORDER((byte) 6, "order", "按单"),
  /**
   * 按柜
   */
  CUPBOARD((byte) 7, "cupboard", "按柜"),
  /**
   * 按立方
   */
  D_CUBE((byte) 8, "d_cube", "按立方"),
  /**
   * 按车辆
   */
  VEHICLE((byte) 9, "vehicle", "按车辆"),
  /**
   * 首重+区间
   */
  FIRST_WEIGHT_INTERVAL((byte) 10, "first_weight_interval", "首重+区间"),
  /**
   * 最低重量
   */
  MINIMUM_WEIGHT((byte) 11, "minimum_weight", "最低重量"),
  /**
   * 重量+补价
   */
  EXTRA_WEIGHT((byte) 12, "extra_weight", "重量+补价"),
  /**
   * 重量+规格
   */
  EXTRA_SPEC((byte) 13, "extra_spec", "重量+规格"),

  /**
   * 附加费
   */
  SURCHARGE((byte) 14, "surcharge", "附加费"),
  /**
   * 运费
   */
  FREIGHT((byte) 15, "freight", "运费"),
  /**
   * 按件
   */
  UNIT((byte) 16, "unit", "按件"),
  /**
   * 按托盘
   */
  TRAY((byte) 17, "tray", "按托盘"),
  /**
   * 采购价费率
   */
  PURCHASE((byte) 18, "purchase", "采购价费率"),
  /**
   * 销售价费率
   */
  SALE((byte) 19, "sale", "销售价费率"),
  /**
   * 集装箱
   */
  CONTAINER((byte) 20, "container", "集装箱"),
  /**
   * 禁区分车费
   */
  PROHIBITED_AREA_FARE((byte) 21, "container_surcharge", "禁区分车费"),
  /**
   * 送货费
   */
  DELIVERY_FEE((byte) 22, "delivery_fee", "送货费"),
  /**
   * 常温送货费
   */
  ROOM_TEMPERATURE_DELIVERY((byte) 23, "room_temperature_delivery", "常温送货费"),
  /**
   * 低温送货费
   */
  LOW_TEMPERATURE_DELIVERY((byte) 24, "low_temperature_delivery", "低温送货费"),
  /**
   * 大件
   */
  LARGE_SIZE((byte) 25, "large_size", "大件"),
  /**
   * 小件
   */
  SMALL_PIECE((byte) 26, "small_piece", "小件"),
  /**
   * 附加费用
   */
  SURCHARGE_FEE((byte) 27, "surcharge_fee", "附加费用"),

    /**
   * 按存货天数
   */
  DAYS((byte) 28, "days", "按存货天数");

  ;

  private final Byte id;
  private final String code;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FeeChargeEnum matchingEnum(String name) {
    for (FeeChargeEnum i : values()) {
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
  public static FeeChargeEnum matchingEnumById(int id) {
    for (FeeChargeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

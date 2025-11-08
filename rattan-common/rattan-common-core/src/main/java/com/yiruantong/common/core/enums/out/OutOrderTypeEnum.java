package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出库单类型枚举
 */
@Getter
@AllArgsConstructor
public enum OutOrderTypeEnum {
  /**
   * 常规出库单
   */
  NORMAL((byte) 1, "常规出库单"),
  /**
   * 无单扫描出库
   */
  NO_BILL((byte) 2, "无单扫描出库"),
  /**
   * 出库单
   */
  OUT_ORDER((byte) 3, "出库单"),
  /**
   * 小程序预约
   */
  MINI_APP((byte) 4, "小程序预约"),
  /**
   * 出库计划转入
   */
  OUT_PLAN_TO_ORDER((byte) 5, "出库计划转入"),

  /**
   * 库内码盘
   */
  STOREHOUSE_STACKING((byte) 6, "库内码盘"),
  /**
   * 报废出库
   */
  INVALIDATE_OUT((byte) 7, "报废出库"),
  /**
   * 领料单
   */
  PICKING((byte) 8, "领料单"),
  /**
   * 出库单拆分
   */
  OUT_SPLIT((byte) 9, "出库单拆分"),
  /**
   * 紧急放行单
   */
  EMERGENCY_RELEASE((byte) 10, "紧急放行单"),
  /**
   * TOB销售单
   */
  SALE_ORDER((byte) 11, "TOB销售单"),
  /**
   * 材料/备件领用单
   */
  SPARE_PARTS_BILL((byte) 12, "材料/备件领用单"),
  /**
   * 材料备件退货
   */
  RETURN_MATERIAL_SPARE_PARTS((byte) 13, "材料备件退货"),
  /**
   * 越库出库
   */
  CROSS_ORDER((byte) 14, "越库出库"),

  /**
   * 头程仓库出库
   */
  FREIGHT_ORDER_STORAGE_OUT((byte) 17, "头程仓库出库");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutOrderTypeEnum matchingEnum(String name) {
    for (OutOrderTypeEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

package com.yiruantong.common.core.enums.finance;

import com.yiruantong.common.core.enums.in.InEnterStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FinanceFieldEnum {
  /**
   * 费用科目
   */
  FEE_ITEM_NAME((byte) 1, "费用科目"),
  /**
   * 计价方式
   */
  PRICING_MANNER((byte) 2, "计价方式"),
  /**
   * 出发地
   */
  PLACE_ORIGIN((byte) 3, "出发地"),
  /**
   * 目的地
   */
  PLACE_DESTINATION((byte) 4, "目的地"),
  /**
   * 计费值
   */
  BILLABLE_VALUE((byte) 5, "计费值"),
  /**
   * 数量
   */
  QUANTITY_ORDER((byte) 6, "数量"),
  /**
   * 承运商
   */
  CARRIER_NAME((byte) 7, "承运商"),
  /**
   * 费用单价
   */
  SALE_PRICE((byte) 8, "费用单价"),
  /**
   * 小计重量
   */
  ROW_WEIGHT((byte) 9, "小计重量"),
  /**
   * 小计体积
   */
  ROW_CUBE((byte) 10, "小计体积"),
  /**
   * 承运商类型
   */
  CARRIER_TYPE((byte) 11, "承运商类型"),
  /**
   * 规则条件结合
   */
  CONJUNCTION((byte) 12, "规则条件结合");
  private final Byte id;
  private final String name;
  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FinanceFieldEnum matchingEnum(String name) {
    for (FinanceFieldEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

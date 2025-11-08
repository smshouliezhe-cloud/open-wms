package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 预到货单类型枚举
 */
@Getter
@AllArgsConstructor
public enum InOrderTypeEnum {
  /**
   * 常规预到货单
   */
  NORMAL((byte) 1, "常规预到货"),
  /**
   * 无单入库
   */
  NO_BILL((byte) 2, "无单入库"),

  /**
   * 无单入库
   */
  OUT_PLANORDER((byte) 3, "出库计划单"),

  /**
   * 小程序预约
   */
  MINI_APP((byte) 4, "小程序预约"),

  /**
   * 入库计划转入
   */
  ORDER_PLAN((byte) 5, "入库计划转入"),

  /**
   * 库内码盘
   */
  STOREHOUSE_STACKING((byte) 6, "库内码盘"),

  /**
   * 缺货转预到货
   */
  LOCK_TO_ORDER((byte) 7, "缺货转预到货"),

  /**
   * 出库退货转预到货
   */
  RETURN_TO_ORDER((byte) 8, "出库退货转入"),
  /**
   * 建议采购转入
   */
  SUGGESTION_SHIFT_TO((byte) 9, "建议采购转入"),
  /**
   * 建议采购
   */
  SUGGESTION((byte) 10, "建议采购"),
  /**
   * 退料单
   */
  RETURN_ORDER((byte) 11, "退料单"),
  /**
   * 成品入库单
   */
  COMPLETION_ORDER((byte) 12, "成品入库单"),
  /**
   * 一键闪入
   */
  NO_BILL_FLASH((byte) 13, "一键闪入"),
  /**
   * 合金/辅料
   */
  AUXILIARY_MATERIAL((byte) 14, "合金/辅料"),
  /**
   * 无单扫描上架
   */
  PDA_NO_BILL_SHELVE((byte) 15, "无单扫描上架"),

  /**
   * 头程仓库入库
   */
  FREIGHT_ORDER_STORAGE_IN((byte) 16, "头程仓库入库");

  private final Byte id;
  private final String name;
}

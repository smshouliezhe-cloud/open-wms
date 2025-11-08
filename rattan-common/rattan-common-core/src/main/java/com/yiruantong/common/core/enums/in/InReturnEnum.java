package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单动作
 */
@Getter
@AllArgsConstructor
public enum InReturnEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核成功
   */
  SUCCESS((byte) 2, "审核成功"),
  /**
   * 审核失败
   */
  FAILED((byte) 3, "审核失败"),
  /**
   * 到货退货单
   */
  RETURN_ORDER((byte) 4, "到货退货单"),
  /**
   * 已转出库
   */
  TO_OUT_ORDER((byte) 5, "已转出库"),
  /**
   * 出库完成
   */
  OUT_FINISHED((byte) 6, "出库完成"),
  /**
   * 终止
   */
  STOP((byte) 7, "终止"),
  /**
   * 部分出库
   */
  PARTIAL_TO_OUT((byte) 8, "部分出库"),
  /**
   * 采购退货
   */
  IN_RETURN((byte) 9, "采购退货"),
  /**
   * 质检退货
   */
  QUALITY_INSPECTION_RETURN((byte) 10, "质检退货"),
  ;

  private final Byte id;
  private final String name;
}

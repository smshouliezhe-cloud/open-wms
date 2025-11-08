package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库计划单状态信息
 */
@Getter
@AllArgsConstructor
public enum InOrderPlanStatusEnum {
  /**
   * 可用
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
   * 待审核
   */
  PENDING((byte) 4, "待审核"),

  /**
   * 已转预到货单
   */
  OVER_TO_INORDER((byte) 5, "已转预到货单"),

  /**
   * 终止
   */
  STOP((byte) 6, "终止"),
  /**
   * 部分交货
   */
  PARTIAL_TO_IN((byte) 7, "部分交货"),
  /**
   * 完全交货
   */
  IN_FINISHED((byte) 8, "完全交货");

  private final Byte id;
  private final String name;
}

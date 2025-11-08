package com.yiruantong.common.core.enums.out;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库计划单状态信息
 */
@Getter
@AllArgsConstructor
public enum OutReturnStatusEnum {
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
   * 已转预到货单
   */
  CONFIRM_IN((byte) 7, "确认入库"),
  /**
   * 部分交货
   */
  PARTIAL_FINISHED((byte) 8, "部分交货"),
  /**
   * 完全交货
   */
  FINISHED((byte) 9, "完全交货"),
  /**
   * 未退款
   */
  NOT_REFUNDED((byte) 10, "未退款"),
  /**
   * 仓管确认
   */
  WAREHOUSE_CONFIRM((byte) 11, "仓管确认"),
  /**
   * 仓管驳回
   */
  WAREHOUSE_REJECT((byte) 12, "仓管驳回"),
  /**
   * 质检确认
   */
  CHECKING_CONFIRM((byte) 13, "质检确认"),
  /**
   * 质检驳回
   */
  CHECKING_REJECT((byte) 14, "质检驳回"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static OutReturnStatusEnum matchingEnum(String name) {
    for (OutReturnStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

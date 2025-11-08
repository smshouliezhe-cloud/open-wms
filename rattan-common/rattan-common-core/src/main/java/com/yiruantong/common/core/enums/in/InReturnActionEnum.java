package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单动作
 */
@Getter
@AllArgsConstructor
public enum InReturnActionEnum {
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
   * 终止
   */
  STOP((byte) 4, "终止"),
  /**
   * 转出库单
   */
  TO_OUT_ORDER((byte) 5, "转出库单"),
  /**
   * 出库
   */
  OUT_ORDER((byte) 5, "出库"),
  /**
   * 开启
   */
  OPEN((byte) 4, "开启"),
  /**
   * 入库质检
   */
  IN_CHECKING((byte) 5, "入库质检");

  private final Byte id;
  private final String name;
}

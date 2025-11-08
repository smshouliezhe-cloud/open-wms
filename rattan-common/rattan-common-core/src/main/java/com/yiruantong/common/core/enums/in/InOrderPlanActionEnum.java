package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单动作
 */
@Getter
@AllArgsConstructor
public enum InOrderPlanActionEnum {
  /**
   * 新建
   */
  NEWED((byte) 1, "新建"),
  /**
   * 审核
   */
  AUDITING((byte) 2, "审核"),
  /**
   * 终止
   */
  STOP((byte) 3, "终止"),
  /**
   * 开启
   */
  OPEN((byte) 4, "开启"),
  /**
   * 导入
   */
  IMPORT((byte) 5, "导入"),
  /**
   * 复制
   */
  COPY((byte) 6, "复制"),
  /**
   * 拆分
   */
  SPLIT((byte) 7, "拆分"),
  /**
   * 转预到货
   */
  TO_ORDER((byte) 8, "转预到货"),
  /**
   * 入库完成回更
   */
  IN_FINISHED_TO_PLAN((byte) 9, "入库完成回更");

  private final Byte id;
  private final String name;


  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InOrderPlanActionEnum matchingEnum(String name) {
    for (InOrderPlanActionEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

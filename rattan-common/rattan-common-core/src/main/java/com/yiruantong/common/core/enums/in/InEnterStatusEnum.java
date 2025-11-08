package com.yiruantong.common.core.enums.in;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 入库单状态枚举
 */
@Getter
@AllArgsConstructor
public enum InEnterStatusEnum {
  /**
   * 可用
   */
  NEWED((byte) 1, "新建"),
  /**
   * 确认入库
   */
  ENTERED((byte) 2, "确认入库"),
  /**
   * 终止执行
   */
  STOPED((byte) 3, "终止执行"),
  /**
   * 部分上架
   */
  PARTIAL_FINISHED((byte) 4, "部分上架"),
  /**
   * 完全上架
   */
  FINISHED((byte) 5, "完全上架"),
  /**
   * 取消入库
   */
  CANCEL((byte) 6, "取消入库");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static InEnterStatusEnum matchingEnum(String name) {
    for (InEnterStatusEnum i : values()) {
      if (i.getName().equals(name)) {
        return i;
      }
    }
    return null;
  }
}

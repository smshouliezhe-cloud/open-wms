package com.yiruantong.common.core.enums.fee;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对账单状态
 */
@Getter
@AllArgsConstructor
public enum FeeStatementStatusEnum {
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
   * 终止
   */
  STOP((byte) 5, "终止");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FeeStatementStatusEnum matchingEnum(String name) {
    for (FeeStatementStatusEnum i : values()) {
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
  public static FeeStatementStatusEnum matchingEnumById(int id) {
    for (FeeStatementStatusEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

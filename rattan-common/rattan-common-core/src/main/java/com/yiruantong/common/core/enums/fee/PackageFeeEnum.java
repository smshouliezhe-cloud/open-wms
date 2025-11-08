package com.yiruantong.common.core.enums.fee;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 费用
 */
@Getter
@AllArgsConstructor
public enum PackageFeeEnum {
  /**
   * 月结账单
   */
  MONTHLY_STATEMENT((byte) 1, "月结账单"),
  /**
   * 每日账单
   */
  DAILY_BILL((byte) 2, "每日账单"),

  /**
   * 仓储费
   */
  STORAGE_FEE((byte) 3, "仓储费"),

  /**
   * 入库一次性费用
   */
  ONE_IN_FEE((byte) 4, "入库一次性费用"),

  /**
   * 出库一次性费用
   */
  ONE_OUT_FEE((byte) 5, "出库一次性费用");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static PackageFeeEnum matchingEnum(String name) {
    for (PackageFeeEnum i : values()) {
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
  public static PackageFeeEnum matchingEnumById(int id) {
    for (PackageFeeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

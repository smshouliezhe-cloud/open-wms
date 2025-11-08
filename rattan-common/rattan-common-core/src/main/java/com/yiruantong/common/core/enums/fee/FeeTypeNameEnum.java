package com.yiruantong.common.core.enums.fee;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单类别
 */
@Getter
@AllArgsConstructor
public enum FeeTypeNameEnum {
  /**
   * 入库单
   */
  IN_ENTER((byte) 1, "入库单"),
  /**
   * 打包单
   */
  OUT_PACKAGE((byte) 2, "打包单"),
  /**
   * 预到货单
   */
  IN_ORDER((byte) 3, "预到货单"),
  /**
   * 出库单
   */
  OUT_ORDER((byte) 4, "出库单"),
  /**
   * 仓储费
   */
  STORAGE_CHARGE((byte) 4, "仓储费"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FeeTypeNameEnum matchingEnum(String name) {
    for (FeeTypeNameEnum i : values()) {
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
  public static FeeTypeNameEnum matchingEnumById(int id) {
    for (FeeTypeNameEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

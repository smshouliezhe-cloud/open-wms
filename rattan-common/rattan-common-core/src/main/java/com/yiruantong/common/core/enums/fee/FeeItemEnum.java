package com.yiruantong.common.core.enums.fee;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 费用
 */
@Getter
@AllArgsConstructor
public enum FeeItemEnum {
  /**
   * 入库费
   */
  IN_FEE((byte) 1, "入库费"),
  /**
   * 入库分拣费
   */
  IN_SORTING_FEE((byte) 2, "入库分拣费"),
  /**
   * 在途虚拟出库分拣
   */
  OUT_FEE((byte) 3, "出库费"),
  /**
   * 出库扫码费
   */
  OUT_SCAN_FEE((byte) 4, "出库扫码费"),
  /**
   * 仓储费
   */
  STORAGE_FEE((byte) 5, "仓储费");

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static FeeItemEnum matchingEnum(String name) {
    for (FeeItemEnum i : values()) {
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
  public static FeeItemEnum matchingEnumById(int id) {
    for (FeeItemEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

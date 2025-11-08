package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * WCS任务类型
 */
@Getter
@AllArgsConstructor
public enum WcsTaskTypeEnum {
  /**
   * 入库任务
   */
  IN_ENTER((byte) 1, "in", "入库任务"),
  /**
   * 出库任务
   */
  OUT_PACKAGE((byte) 2, "out", "出库任务"),
  /**
   * 盘点任务
   */
  STORAGE_CHECK((byte) 3, "move", "盘点任务");

  private final Byte id;
  private final String code;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static WcsTaskTypeEnum matchingEnumByName(String name) {
    for (WcsTaskTypeEnum i : values()) {
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
  public static WcsTaskTypeEnum matchingEnumById(int id) {
    for (WcsTaskTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

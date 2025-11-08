package com.yiruantong.common.core.enums.system;

import cn.hutool.core.util.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 导入扩展字段类型枚举类
 */
@Getter
@AllArgsConstructor
public enum ImportExpandFieldTypeEnum {
  /**
   * 主表物理字段
   */
  MAIN_FACT_FIELD((byte) 0, "主表物理字段"),
  /**
   * 主表扩展字段
   */
  MAIN_EXPEND_FIELD((byte) 1, "主表扩展字段"),
  /**
   * 明细扩展字段
   */
  DETAIL_EXPEND_FIELD((byte) 2, "明细扩展字段"),
  /**
   * 明细物理字段
   */
  DETAIL_FACT_FIELD((byte) 3, "明细物理字段"),
  ;

  private final Byte id;
  private final String name;

  /**
   * 根据名称匹配对应的枚举类
   *
   * @param name 值
   * @return 枚举
   */
  public static ImportExpandFieldTypeEnum matchingEnumByName(String name) {
    for (ImportExpandFieldTypeEnum i : values()) {
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
  public static ImportExpandFieldTypeEnum matchingEnumById(int id) {
    for (ImportExpandFieldTypeEnum i : values()) {
      if (ObjectUtil.equal(i.getId(), id)) {
        return i;
      }
    }
    return null;
  }
}

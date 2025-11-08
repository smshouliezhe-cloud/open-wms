package com.yiruantong.common.core.enums.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单模块枚举
 */
@Getter
@AllArgsConstructor
public enum ImportValidateTypeEnum {
  /**
   * 正则验证
   */
  REGULAR_VALIDATION(1, "正则验证"),
  /**
   * 字典验证
   */
  DICTIONARY_VALIDATION(2, "字典验证"),
  /**
   * 公式运算
   */
  FORMULA_OPERATION(3, "公式运算"),
  /**
   * 字典带入
   */
  DICTIONARY_BRING_IN(4, "字典带入"),
  /**
   * 字典翻译
   */
  DICTIONARY_TRANSLATE(5, "字典翻译"),
  /**
   * SQL更新
   */
  SQL_UPDATE(6, "SQL更新"),
  /**
   * JSON更新
   */
  JSON_UPDATE(6, "JSON更新"),
  /**
   * JSON更新
   */
  DEFAULT_VALUE(7, "默认值");

  private final Integer id;
  private final String name;
}

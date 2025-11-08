package com.yiruantong.common.mybatis.enums;

import lombok.Getter;

/**
 * @Description: 数据类型 @Author: 系统吧 @CreateDate: 2023/05/27 19:29 @Version: 1.0
 */
@Getter
public enum DataTypeEnum {
  /** 布尔 */
  BOOLEAN("boolean", "boolean"),
  /** 字符 */
  CHAR("char", "char"),
  /** 字节 */
  BYTE("byte", "byte"),
  /** 短整型 */
  SHORT("short", "short"),
  /** 整型 */
  INT("int", "int"),
  /** 整型 */
  INTEGER("integer", "integer"),
  /** 长整型 */
  LONG("long", "long"),
  /** 长整型 */
  BIGDECIMAL("bigDecimal", "bigDecimal"),
  /** 浮点 */
  FLOAT("float", "float"),
  /** 双精度 */
  DOUBLE("double", "double"),
  /** 字符串 */
  STRING("string", "string"),
  /** 日期时间 */
  DATETIME("datetime", "datetime"),
  /** 日期 */
  DATE("date", "date");

  private String code;
  private String explain;

  DataTypeEnum(String code, String explain) {
    this.code = code;
    this.explain = explain;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }
}

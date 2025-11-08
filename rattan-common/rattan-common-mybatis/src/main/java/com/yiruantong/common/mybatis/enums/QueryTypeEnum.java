package com.yiruantong.common.mybatis.enums;

import lombok.Getter;

/**
 * @Description: 搜索条件 @Author: 系统吧 @CreateDate: 2023/05/27 19:29 @Version: 1.0
 */
@Getter
public enum QueryTypeEnum {
  /**
   * 等于
   */
  SELECT("select", "select"),
  /**
   * 等于
   */
  EQ("eq", "eq"),
  /**
   * 不等于
   */
  NE("ne", "ne"),
  /**
   * 大于
   */
  GT("gt", "gt"),
  /**
   * 大于等于
   */
  GE("ge", "ge"),
  /**
   * 小于
   */
  LT("lt", "lt"),
  /**
   * 小于等于
   */
  LE("le", "le"),
  /**
   * 两者之间
   */
  BETWEEN("between", "between"),
  /**
   * 不在这两者之间
   */
  NOTBETWEEN("notBetween", "notBetween"),
  /**
   * 模糊匹配
   */
  LIKE("like", "like"),
  /**
   * 不包含某个值的模糊匹配
   */
  NOTLIKE("notLike", "notLike"),
  /**
   * 左边值模糊匹配
   */
  LIKELEFT("likeLeft", "likeLeft"),
  /**
   * 右边值模糊匹配
   */
  LIKERIGHT("likeRight", "likeRight"),
  /**
   * 为空
   */
  ISNULL("isNull", "isNull"),
  /**
   * 不为空
   */
  ISNOTNULL("isNotNull", "isNotNull"),
  /**
   * 在某个范围里面
   */
  IN("in", "in"),
  /**
   * 不在某个范围里面
   */
  NOTIN("notIn", "notIn"),
  /**
   * 升序
   */
  ORDERBYASC("orderByAsc", "orderByAsc"),
  /**
   * 降序
   */
  ORDERBYDESC("orderByDesc", "orderByDesc"),
  /**
   * 排序
   */
  ORDERBY("orderBy", "orderBy"),
  /**
   * AND子条件查询
   */
  SUBQUERY("subQuery", "subQuery"),
  /**
   * 自定查询
   */
  CUSTOM("custom", "custom"),
  /**
   * exists查询
   */
  EXISTS("exists", "exists"),
  /**
   * notexists查询
   */
  NOTEXISTS("notexists", "notexists"),
  /**
   * 分组
   */
  GROUPBY("groupby", "groupby"),
  /**
   * max取最大值
   */
  MAX("max", "max"),
  /**
   * min取最小值
   */
  MIN("min", "min"),
  /**
   * 查询树
   */
  TREE("tree", "tree"),
  /**
   * 扩展字段
   */
  EXPANDFIELDS("expandfields", "expandfields"),
  ;

  private String code;
  private String explain;

  QueryTypeEnum(String code, String explain) {
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

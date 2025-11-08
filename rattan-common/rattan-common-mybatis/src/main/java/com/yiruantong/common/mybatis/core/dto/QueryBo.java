package com.yiruantong.common.mybatis.core.dto;

import com.yiruantong.common.mybatis.enums.DataTypeEnum;
import com.yiruantong.common.mybatis.enums.QueryTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * @Description: 查询条件 @Author: 谢天保 @CreateDate: 2023/05/27 19:15 @Version: 1.0
 */
@Data
public class QueryBo {
  /**
   * 表别名
   */
  private String tableAlias;
  /**
   * 字段名
   */
  private String column;
  /**
   * 值
   */
  private String values;
  /**
   * 查询类型，运算符
   */
  private QueryTypeEnum queryType;
  /**
   * 数据类型
   */
  private DataTypeEnum dataType;
  /**
   * 是or查询
   */
  private boolean isOr;
  /**
   * 子查询条件
   */
  private List<QueryBo> subQueryBo;
}

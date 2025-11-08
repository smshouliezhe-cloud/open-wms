package com.yiruantong.common.mybatis.core.domain.bo;

import lombok.Data;

@Data
public class SaveDetailParamBo {
  /** 明细主键字段名 */
  private String idField;

  /** 明细索引页 */
  private int pageIndex;

  /** 明细分页大小 */
  private int pageSize;

  /** 显示求和字段 */
  private int showSumField;

  /** 明细表名 */
  private String subTableName;

  /** 标题名称 */
  private String title;

  /** 排序列 */
  private String orderByColumn;

  /** 排序的方向desc或者asc */
  private String isAsc;
}

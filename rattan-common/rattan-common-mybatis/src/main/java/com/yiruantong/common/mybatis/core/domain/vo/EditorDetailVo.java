package com.yiruantong.common.mybatis.core.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 编辑页面视图类
 *
 * @author YiRuanTong
 */
@Data
public class EditorDetailVo<V> implements Serializable {

  /**
   * 明细集合
   */
  Object detailList;
  /**
   * 总记录数
   */
  private long total;
  /**
   * 表名
   */
  private String tableName;
}

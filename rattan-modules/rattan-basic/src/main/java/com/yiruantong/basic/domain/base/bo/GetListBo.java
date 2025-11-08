package com.yiruantong.basic.domain.base.bo;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * getList Bo对象
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
public class GetListBo {

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 查询显示条数
   */
  private Long take;

  /**
   * 查询条件name
   */
  private String name;

  /**
   * 查询条件term
   */
  private String term;

  /**
   * 查询条件类型
   */
  private String type;

  /**
   * 倒序
   */
  private boolean isDesc;

  /**
   * 查询字段
   */
  private String searchFields;
}

package com.yiruantong.common.core.domain.model;

import lombok.Data;

/**
 * 基础Vo对象
 */
@Data
public class BaseVo {
  /**
   * id值
   */
  private Long id;
  /**
   * code值
   */
  private String code;
  /**
   * name值
   */
  private String name;
  /**
   * label名称
   */
  private String label;
  /**
   * 备注
   */
  private String remark;
}

package com.yiruantong.common.core.domain.model;

import lombok.Data;

import java.util.List;

/**
 * 基础Bo对象
 */
@Data
public class BaseBo {
  /**
   * id值
   */
  private Long id;
  /**
   * ids值
   */
  private List<Long> ids;
  /**
   * code值
   */
  private String code;
  /**
   * name值
   */
  private String name;
  /**
   * 备注
   */
  private String remark;
}

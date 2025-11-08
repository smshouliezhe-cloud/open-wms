package com.yiruantong.inventory.domain.helper.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列信息 Type
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class Type {

  /**
   * 类别ID
   */
  private Long typeId;

  /**
   * 类别名称
   */
  private String typeName;
}

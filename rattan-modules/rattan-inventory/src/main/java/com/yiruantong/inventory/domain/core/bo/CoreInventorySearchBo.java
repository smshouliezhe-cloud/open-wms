package com.yiruantong.inventory.domain.core.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * API库存明细查询传到后端的参数
 *
 * @author YiRuanTong
 * @date 2024-09-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CoreInventorySearchBo {

  /**
   * name
   */
  private String name;
  /**
   * amis关键词
   */
  private String term;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 商品ID
   */
  private Long productId;

  /**
   * 查询字段
   */
  private String searchFields;

  /**
   * 货位类型
   */
  private String positionTypes;

  /**
   * 查询条数
   */
  private Integer take;

  /**
   * 是否根据 仓库货位分组
   */
  private Boolean isGroup;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 货位
   */
  private String positionName;
}

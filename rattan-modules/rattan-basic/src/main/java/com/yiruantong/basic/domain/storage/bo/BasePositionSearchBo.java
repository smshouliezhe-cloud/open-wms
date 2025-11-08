package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.base.PositionTypeEnum;


/**
 * 货位查询对象
 *
 * @author YiRuanTong
 * @date 2024-10-25
 */
@Data
@NoArgsConstructor
public class BasePositionSearchBo {

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
   * 查询字段
   */
  private String searchFields;

  /**
   * 货位类型
   */
  private String positionTypes;

  /**
   * 货位类型
   */
  private byte positionType;

  /**
   * 货位枚举类型
   */
  private PositionTypeEnum positionTypeEnum;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 查询条数
   */
  private Integer take;

  /**
   * 是否上架
   */
  private boolean isOnShelve;
}

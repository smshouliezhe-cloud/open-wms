package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按货位类型推荐 PositionList
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class PositionList {

  /**
   * id
   */
  private Long id;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 货位
   */
  private String positionName;

  /**
   * 库区
   */
  private String areaCode;

  /**
   * 通道
   */
  private String channelCode;

  /**
   * 货架号
   */
  private String shelveCode;

  /**
   * 排序号
   */
  private Long orderNum;
}

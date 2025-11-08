package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * 货架数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ShelveDataBo {
  /**
   * 货架信息ID
   */
  private Long storageShelveId;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 库区名称
   */
  private String areaCode;

  /**
   * 通道编号
   */
  private String channelCode;

  /**
   * 货架编号
   */
  private String shelveCode;

  /**
   * 列数
   */
  private Long columnNum;
  /**
   * 层数
   */
  private Long rowNum;

  /**
   * 列编码规则
   */
  private String columnRegular;

  /**
   * 货位类型
   */
  private String positionType;

  /**
   * 货位编码规则
   */
  private String positionRegular;

  /**
   * 最大容量
   */
  private Long maxCapacity;

  /**
   * 货架编码
   */
  private String shelvesRegular;

  /**
   * 通道编码规则
   */
  private String channelRegular;

  /**
   * 行编码规则
   */
  private String rowRegular;

  /**
   * 排序号
   */
  private Long orderNo;

  /**
   * 记录操作
   */
  private Long recordAction;
  /**
   * 货位编码规则集合
   */
  private ArrayList<ColumnConfigBo> columnConfigs;

  private ArrayList<RowConfigsBo> rowConfigs;
}

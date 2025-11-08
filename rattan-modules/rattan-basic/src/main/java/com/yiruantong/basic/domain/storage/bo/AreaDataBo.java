package com.yiruantong.basic.domain.storage.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 库区信息
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AreaDataBo {
  /**
   * 库区ID
   */
  private Long storageAreaId;
  /**
   * 库区
   */
  private String areaCode;

  /**
   * 仓库ID
   */
  private Long storageId;
  /**
   * 仓库
   */
  private String storageName;

  /**
   * 货位类型
   */
  private Long positionType;
  /**
   * 编码规则
   */
  private String positionRegular;

  /**
   * 摆放模式
   */
  private String shelveMode;
  /**
   * 拣货模式
   */
  private String pickMode;

  /**
   * 分拣权重
   */
  private String orderNo;
  /**
   * 通道数
   */
  private Long channelNum;

  /**
   * 列数
   */
  private Long columnNum;
  /**
   * A面
   */
  private Long isALine;

  /**
   * B面
   */
  private Long isBLine;
  /**
   * A面架数开始数
   */
  private Long shelveNumA1;
  /**
   * A面架数结束数
   */
  private Long shelveNumA2;
  /**
   * B面架数开始数
   */
  private Long shelveNumB1;
  /**
   * B面架数结束数
   */
  private Long shelveNumB2;
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
   * 列编码规则
   */
  private String columnRegular;
  /**
   * 最大库位量
   */
  private BigDecimal maxCapacity;
  /**
   * 温层类型
   */
  private String thermocLine;
  /**
   * 货位最大重量
   */
  private BigDecimal maxWeight;
  /**
   * 货位最大拍数
   */
  private Long maxBeatNumber;
  /**
   * 存货率计算
   */
  private String inventoryRate;
  /**
   * x6Data
   */
  private String x6Data;
  /**
   * jsonData
   */
  private String jsonData;

  /**
   * rowTitle
   */
  private String rowTitle;
  /**
   * channelNumDidui
   */
  private Long channelNumDidui;
  /**
   * rowNumDidui
   */
  private Long rowNumDidui;
  /**
   * columnNumDidui
   */
  private Long columnNumDidui;
  /**
   * 上传文件
   */
  private String svgUrl;

  /**
   * action
   */
  private String action;
  /**
   * rowNum
   */
  private Long rowNum;
  /**
   * RowConfigs
   */
  private String RowConfigs;
}

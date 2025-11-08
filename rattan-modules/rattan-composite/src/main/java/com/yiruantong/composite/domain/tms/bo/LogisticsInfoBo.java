package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 表单上面的数据
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LogisticsInfoBo {

  /**
   * 分段ID
   */
  private String valueId;

  /**
   * 运单号
   */
  private String wayBillCode;

  /**
   * 货代公司
   */
  private String carrierName;

  /**
   * 计价方式
   */
  private String pricingManner;

  /**
   * 物流方式
   */
  private String logisticsMode;

  /**
   * 出发地
   */
  private String placeOrigin;

  /**
   * 目的地
   */
  private String siteName;

  /**
   * 价目分段类型
   */
  private String priceSegmentType;

  /**
   * 运输渠道
   */
  private String expressCorpName;

  /**
   * 车型
   */
  private String vehicleType;

  /**
   * 费用类型
   */
  private String expenseType;

  /**
   * 费用说明
   */
  private String costShow;

  /**
   * 备注
   */
  private String detailRemark;

  /**
   * 合计运输费用
   */
  private BigDecimal totalCost;
  /**
   * 线路
   */
  private String lineName;

  /**
   * 仓库名称
   */
  private String storageName;


  /**
   * 单据数
   */
  private BigDecimal wayBillCodeCount;

  /**
   * 结算方式
   */
  private String settlementMode;

  /**
   * 实际分段运费
   */
  private BigDecimal segmentFreight;

  /**
   * 币种
   */
  private String currencyCode;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 合计体积
   */
  private BigDecimal totalVolume;
}

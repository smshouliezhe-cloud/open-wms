package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 应付账单添加费用BO
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LogisticsDataBo {
  /**
   * 账单id
   */
  private Long reimburseId;

  /**
   * 承运商
   */
  private String carrierName;

  /**
   * 费用科目
   */
  private String feeItemName;

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
  private String distributionSite;

  /**
   * 目的地
   */
  private String unloadSite;

  /**
   * 计费值
   */
  private BigDecimal billableValue;

  /**
   * 包装规格
   */
  private String packingMethod;
}

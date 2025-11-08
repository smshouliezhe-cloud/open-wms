package com.yiruantong.composite.domain.fee;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 运费信息
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Data

public class FinanceInfo implements Serializable {

  /**
   * 费用单价
   */
  private String feeItemName;

  /**
   * 规则类型
   */
  private String formulaType;

  /**
   * 计价方式
   */
  private String pricingManner;

  /**
   * 出发网点
   */
  private String placeOrigin;

  /**
   * 目的地网点
   */
  private String placeDestination;

  /**
   * 运费类型
   */
  private String freightType;

  /**
   * 费用单价
   */
  private BigDecimal salePrice;

  /**
   * 小计费用
   */
  private BigDecimal saleAmount;

  /**
   * 计费值
   */
  private BigDecimal billableValue;

  /**
   * 数量
   */
  private Long quantityOrder;

  /**
   * 承运商
   */
  private String carrierName;

  /**
   * 小计重量
   */
  private BigDecimal rowWeight;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;
}

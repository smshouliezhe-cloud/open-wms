package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 表单明细表一，物流分段明细
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LogisticsCostBo {
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
  private String placeOrigin;

  /**
   * 目的地
   */
  private String placeDestination;

  /**
   * 容器类型
   */
  private String plateType;

  /**
   * 容器规格
   */
  private String plateSpec;

  /**
   * 备注
   */
  private String detailRemark;

  /**
   * 实际分段运费
   */
  private BigDecimal segmentFreight;
  /**
   * 计费重（kg）
   */
  private BigDecimal chargingWeight;
  /**
   * 计费价格
   */
  private BigDecimal chargingPrice;
  /**
   * 体积重
   */
  private BigDecimal bulkWeight;
  /**
   * 车型
   */
  private String vehicleType;
  /**
   * 运价类型
   */
  private String freightType;
  /**
   * 附加金额
   */
  private BigDecimal surcharge;
  /**
   * 计费值
   */
  private BigDecimal billableValue;
  /**
   * 单价
   */
  private BigDecimal salePrice;
  /**
   * 小计金额
   */
  private BigDecimal saleAmount;
  /**
   * 合计数量
   */
  private BigDecimal quantityOrder;
  /**
   * 合计重量
   */
  private BigDecimal rowWeight;
  /**
   * 合计体积
   */
  private BigDecimal rowCube;
  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;
  /**
   * 客户单号
   */
  private String customerOrderCode;
  /**
   * 包装规格
   */
  private String packingMethod;
  /**
   * 运单编号
   */
  private List<String> wayBillCode;

}

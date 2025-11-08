package com.yiruantong.basic.domain.tms;

import lombok.Data;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseFreightDetailExtendVo {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 模板明细ID
   */
  private Long templateDetailId;

  /**
   * 模板ID
   */
  private Long templateId;

  /**
   * 计价方式
   */
  private String pricingManner;

  /**
   * 物流方式
   */
  private String logisticsMode;

  /**
   * 数值开始
   */
  private BigDecimal beganQuantity;

  /**
   * 数值结束
   */
  private BigDecimal endQuantity;

  /**
   * 价格
   */
  private BigDecimal price;

  /**
   * 出发地
   */
  private String placeOrigin;

  /**
   * 目的地
   */
  private String placeDestination;

  /**
   * 报关费
   */
  private BigDecimal customsCharges;

  /**
   * 其它费用
   */
  private BigDecimal otherCosts;

  /**
   * 头程重量
   */
  private BigDecimal firstWeight;

  /**
   * 头程价格
   */
  private BigDecimal firstPrice;

  /**
   * 装箱方式
   */
  private String packUsing;

  /**
   * 集装箱尺寸
   */
  private String containerSize;

  /**
   * 车辆规格
   */
  private String vehicleSpec;

  /**
   * 清关方式
   */
  private String customsType;

  /**
   * 清关金额/票
   */
  private BigDecimal customsAmount;

  /**
   * 关税
   */
  private BigDecimal tariffs;

  /**
   * 时效
   */
  private BigDecimal prescription;

  /**
   * 备注
   */
  private String remark;

  /**
   * 物流公司ID
   */
  private Long expressCorpId;

  /**
   * 物流公司
   */
  private String expressCorpName;

  /**
   * 运价类型
   */
  private String freightType;

  /**
   * 车型
   */
  private String vehicleType;

  /**
   * 价目分段类型
   */
  private String priceSegmentType;

  /**
   * 币种
   */
  private String currencyCode;

  /**
   * 起重
   */
  private BigDecimal startWeight;

  /**
   * 最低收费
   */
  private BigDecimal minCharge;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 出发地id
   */
  private Long placeOriginId;

  /**
   * 目的地id
   */
  private Long placeDestinationId;

  /**
   * 费用科目id
   */
  private Long feeItemId;

  /**
   * 费用科目
   */
  private String feeItemName;

  /**
   * 车型id
   */
  private Long vehicleId;

  /**
   * 自动生成运费
   */
  private Byte autoBuildFreight;

  /**
   * plate_id
   */
  private Long plateId;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 容器规格
   */
  private String plateSpec;


}

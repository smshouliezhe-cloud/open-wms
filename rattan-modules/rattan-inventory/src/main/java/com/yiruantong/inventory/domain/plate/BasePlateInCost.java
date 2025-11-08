package com.yiruantong.inventory.domain.plate;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 容器归还费用明细对象 base_plate_in_cost
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_in_cost", autoResultMap = true)
public class BasePlateInCost extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 费用明细id
   */
  @TableId(value = "cost_id")
  private Long costId;

  /**
   * 容器归还id
   */
  private Long inId;

  /**
   * 费用科目id
   */
  private Long feeItemId;

  /**
   * 费用科目
   */
  private String feeItemName;

  /**
   * 计费方式
   */
  private String pricingManner;

  /**
   * 费用单价
   */
  private BigDecimal costPrice;

  /**
   * 小计费用
   */
  private BigDecimal subCost;

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
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 计费值
   */
  private BigDecimal billableValue;

  /**
   * 合计件数
   */
  private BigDecimal totalPackage;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 容器规格
   */
  private String plateSpec;

  /**
   * 容器id
   */
  private Long plateId;

  /**
   * 容器名称
   */
  private String plateName;


}

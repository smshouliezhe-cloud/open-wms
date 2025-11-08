package com.yiruantong.basic.domain.product;

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
 * 商品套装明细对象 base_product_set_detail
 *
 * @author YRT
 * @date 2023-12-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_product_set_detail", autoResultMap = true)
public class BaseProductSetDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 商品拆分明细ID
   */
  @TableId(value = "product_set_detail_id")
  private Long productSetDetailId;

  /**
   * 商品拆分ID
   */
  private Long productSetId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 产品条码
   */
  private String productModel;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 销售售价
   */
  private BigDecimal salePrice;

  /**
   * 原始售价
   */
  private BigDecimal originalPrice;

  /**
   * 拆分数量
   */
  private Long splitQuantity;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

  /**
   * 成本价
   */
  private BigDecimal purchasePrice;

  /**
   * 权重
   */
  private BigDecimal orderNumber;

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


}

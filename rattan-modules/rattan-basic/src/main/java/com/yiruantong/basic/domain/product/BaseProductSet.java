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
 * 商品套装主对象 base_product_set
 *
 * @author YRT
 * @date 2023-12-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_product_set", autoResultMap = true)
public class BaseProductSet extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 商品拆分ID
   */
  @TableId(value = "product_set_id")
  private Long productSetId;

  /**
   * 订单渠道
   */
  private Byte orderChannel;

  /**
   * ERP店铺ID
   */
  private Long storeInfoId;

  /**
   * 店铺名称
   */
  private String storeName;

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
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名字
   */
  private String consignorName;

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
   * 产品型号
   */
  private String productBarCode;

  /**
   * 图片
   */
  private String images;


}

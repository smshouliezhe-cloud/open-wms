package com.yiruantong.basic.domain.storage;

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
 * 商品容器管理对象 base_plate_product
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_product", autoResultMap = true)
public class BasePlateProduct extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 商品容器id
   */
  @TableId(value = "plate_product_id")
  private Long plateProductId;

  /**
   * 商品id
   */
  private Long productId;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品规格
   */
  private String productSpec;

  /**
   * 商品条码
   */
  private String productModel;

  /**
   * 容器id
   */
  private Long plateId;

  /**
   * 容器编号
   */
  private String plateCode;

  /**
   * 容器类型
   */
  private String plateType;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 仓库id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

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
   * 容器名称（规格）
   */
  private String plateSpec;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 容器名称
   */
  private String plateName;


}

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
 * 返厂出库记录对象 base_plate_return_out
 *
 * @author YRT
 * @date 2024-04-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_return_out", autoResultMap = true)
public class BasePlateReturnOut extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 返厂出库记录
   */
  @TableId(value = "out_detail_id")
  private Long outDetailId;

  /**
   * 外键id
   */
  private Long returnFactoryId;

  /**
   * 客户id
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户简称
   */
  private String clientShortName;

  /**
   * 销售区域id
   */
  private Long consignorIdSale;

  /**
   * 销售区域编号
   */
  private String consignorCodeSale;

  /**
   * 销售区域名称
   */
  private String consignorNameSale;

  /**
   * 容器规格
   */
  private String plateSpec;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 返厂出库数量
   */
  private Long returnFactoryQty;

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
   * 运费单价
   */
  private BigDecimal price;

  /**
   * 小计运费
   */
  private BigDecimal subFreight;

  /**
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 小计重量
   */
  private BigDecimal rowWeight;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 容器名称
   */
  private String plateName;


}

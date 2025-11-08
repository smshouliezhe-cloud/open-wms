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
 * 容器返厂单明细对象 base_plate_return_factory_detail
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_return_factory_detail", autoResultMap = true)
public class BasePlateReturnFactoryDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器返厂明细id
   */
  @TableId(value = "return_factory_detail_id")
  private Long returnFactoryDetailId;

  /**
   * 容器返厂id
   */
  private Long returnFactoryId;

  /**
   * 容器id
   */
  private Long plateId;

  /**
   * 容器名称
   */
  private String plateName;

  /**
   * 容器编号
   */
  private String plateCode;

  /**
   * 容器规格
   */
  private String plateSpec;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 返厂数量
   */
  private BigDecimal returnFactoryQty;

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
   * 单位重量
   */
  private BigDecimal weight;

  /**
   * 单位体积
   */
  private BigDecimal unitCube;

  /**
   * 小计重量
   */
  private BigDecimal rowWeight;

  /**
   * 小计体积
   */
  private BigDecimal rowCube;

  /**
   * 运费单价
   */
  private BigDecimal price;

  /**
   * 小计运费
   */
  private BigDecimal subFreight;

  /**
   * 费用单价
   */
  private BigDecimal costPrice;

  /**
   * 小计费用
   */
  private BigDecimal subCost;

  /**
   * 容器属性
   */
  private String plateAttribute;


}

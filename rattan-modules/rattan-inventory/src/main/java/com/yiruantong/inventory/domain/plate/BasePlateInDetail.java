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
 * 容器归还明细对象 base_plate_in_detail
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_in_detail", autoResultMap = true)
public class BasePlateInDetail extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器归还明细Id
   */
  @TableId(value = "in_detail_id")
  private Long inDetailId;

  /**
   * 容器归还Id
   */
  private Long inId;

  /**
   * 类型
   */
  private String plateType;

  /**
   * 现借出数量
   */
  private BigDecimal nowOutQty;

  /**
   * 归还数量
   */
  private BigDecimal returnQty;

  /**
   * 剩余借出数量
   */
  private BigDecimal surplusOutQty;

  /**
   * SN
   */
  private String singleSignCode;

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
   * 小计运费
   */
  private BigDecimal subFreight;

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
   * 来源主表id
   */
  private Long sourceMainId;

  /**
   * 来源明细id
   */
  private Long sourceDetailId;

  /**
   * 容器属性
   */
  private String plateAttribute;


}

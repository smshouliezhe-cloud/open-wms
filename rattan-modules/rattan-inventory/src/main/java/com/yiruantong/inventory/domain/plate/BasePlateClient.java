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
 * 客户容器管理对象 base_plate_client
 *
 * @author YRT
 * @date 2024-03-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_client", autoResultMap = true)
public class BasePlateClient extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 客户容器Id
   */
  @TableId(value = "client_plate_id")
  private Long clientPlateId;

  /**
   * 客户Id
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户名称
   */
  private String clientShortName;

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 容器类别
   */
  private String plateType;

  /**
   * 容器借出数量
   */
  private BigDecimal outerOty;

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
   * 容器名称
   */
  private String plateName;

  /**
   * 容器编号
   */
  private String plateCode;

  /**
   * 销售组织
   */
  private String consignorNameSale;

  /**
   * 销售组织编号
   */
  private String consignorCodeSale;

  /**
   * 销售组织ID
   */
  private Long consignorIdSale;

  /**
   * 容器id
   */
  private Long plateId;

  /**
   * 容器规格
   */
  private String plateSpec;


}

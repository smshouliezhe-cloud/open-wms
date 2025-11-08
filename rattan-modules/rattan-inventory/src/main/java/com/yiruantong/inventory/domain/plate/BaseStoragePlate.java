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
 * 仓库容器查询对象 base_storage_plate
 *
 * @author YRT
 * @date 2024-05-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_storage_plate", autoResultMap = true)
public class BaseStoragePlate extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 主键字段id
   */
  @TableId(value = "storage_plate_id")
  private Long storagePlateId;

  /**
   * 执行单id
   */
  private Long billId;

  /**
   * 执行单号
   */
  private String billCode;

  /**
   * 仓库id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 来源类型
   */
  private String sourceType;

  /**
   * 入库数量
   */
  private BigDecimal enterQuantity;

  /**
   * 返厂数量
   */
  private BigDecimal returnFactoryQty;

  /**
   * 未返厂数量
   */
  private BigDecimal unReturnFactoryQty;

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
   * 容器名称
   */
  private String plateName;

  /**
   * 容器属性
   */
  private String plateAttribute;


}

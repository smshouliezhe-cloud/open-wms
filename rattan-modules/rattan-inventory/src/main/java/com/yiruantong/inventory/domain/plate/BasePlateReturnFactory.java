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
 * 容器返厂单对象 base_plate_return_factory
 *
 * @author YRT
 * @date 2024-03-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_return_factory", autoResultMap = true)
public class BasePlateReturnFactory extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 返厂id
   */
  @TableId(value = "return_factory_id")
  private Long returnFactoryId;

  /**
   * 返厂单号
   */
  private String returnFactoryCode;

  /**
   * 采购商ID
   */
  private Long providerId;

  /**
   * 采购商编号
   */
  private String providerCode;

  /**
   * 采购商名称
   */
  private String providerShortName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 返厂数量
   */
  private BigDecimal totalReturnFactoryQty;

  /**
   * 返厂日期
   */
  private Date returnFactoryDate;

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
   * 备注
   */
  private String remark;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Byte auditing;

  /**
   * 审核时间
   */
  private Date auditDate;

  /**
   * 审核备注
   */
  private String auditRemark;

  /**
   * 单据状态
   */
  private String statusText;

  /**
   * 合计费用
   */
  private BigDecimal totalCost;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 承运商ID
   */
  private Long carrierId;

  /**
   * 承运商
   */
  private String carrierName;

  /**
   * 运输车型
   */
  private String vehicleType;

  /**
   * 车辆id
   */
  private Long vehicleId;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 司机id
   */
  private Long driverId;

  /**
   * 司机名称
   */
  private String driverName;

  /**
   * 司机电话
   */
  private String driverTel;

  /**
   * 合计运费
   */
  private BigDecimal totalFreight;

  /**
   * 出发地id
   */
  private Long placeOriginId;

  /**
   * 出发地
   */
  private String placeOrigin;

  /**
   * 目的地id
   */
  private Long placeDestinationId;

  /**
   * 目的地
   */
  private String placeDestination;

  /**
   * 集装箱号
   */
  private String containerNo;


}

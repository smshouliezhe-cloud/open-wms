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
 * 容器归还主对象 base_plate_in
 *
 * @author YRT
 * @date 2024-05-11
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_in", autoResultMap = true)
public class BasePlateIn extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器归还Id
   */
  @TableId(value = "in_id")
  private Long inId;

  /**
   * 容器归还编号
   */
  private String inCode;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 类型
   */
  private String plateType;

  /**
   * 状态
   */
  private String statusText;

  /**
   * 合计归还数量
   */
  private BigDecimal totalReturnQty;

  /**
   * 归还日期
   */
  private Date returnDate;

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
   * 审核日期
   */
  private Date auditDate;

  /**
   * 仓库Id
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
   * 源借出单号
   */
  private String sourceOutCode;

  /**
   * 运输类别
   */
  private String transportType;

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
  private String totalFreight;

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
   * 源借出单id
   */
  private Long sourceOutId;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 计价方式
   */
  private String pricingManner;

  /**
   * 送货订单号
   */
  private String deliveryCode;

  /**
   * 容器借出单id
   */
  private Long outId;

  /**
   * 订单日期
   */
  private Date applyDate;

  /**
   * 单据类型
   */
  private String orderType;

  /**
   * 返空桶差异备注
   */
  private String differenceRemark;


}

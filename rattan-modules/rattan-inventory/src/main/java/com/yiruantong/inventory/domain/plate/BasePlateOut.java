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
 * 容器借出主对象 base_plate_out
 *
 * @author YRT
 * @date 2024-05-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_plate_out", autoResultMap = true)
public class BasePlateOut extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 容器借出Id
   */
  @TableId(value = "out_id")
  private Long outId;

  /**
   * 借出编号
   */
  private String outCode;

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
   * 合计借出数量
   */
  private BigDecimal totalOutQty;

  /**
   * 借出日期
   */
  private Date outDate;

  /**
   * 备注
   */
  private String remark;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

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
   * 来源id
   */
  private Long sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 来源类型
   */
  private String sourceType;

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
   * 已归还数量
   */
  private BigDecimal totalReturnedQty;

  /**
   * 未归还数量
   */
  private BigDecimal totalUnreturnedQty;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 客户单号
   */
  private String customerOrderCode;

  /**
   * 订单日期
   */
  private Date applyDate;


}

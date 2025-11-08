package com.yiruantong.inbound.domain.service;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 退货单对象 in_return
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_return", autoResultMap = true)
public class InReturn extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 退货ID
   */
  @TableId(value = "return_id")
  private Long returnId;

  /**
   * 退货编号
   */
  private String returnCode;

  /**
   * 采购单ID
   */
  private Long orderId;

  /**
   * 采购单编号
   */
  private String orderCode;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 申请日期
   */
  private Date applyDate;

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
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;

  /**
   * 状态
   */
  private String returnStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

  /**
   * 货主ID
   */
  private Long consignorId;

  /**
   * 货主编号
   */
  private String consignorCode;

  /**
   * 货主名称
   */
  private String consignorName;

  /**
   * 退货数量
   */
  private BigDecimal totalReturnQuantity;

  /**
   * 退货金额
   */
  private BigDecimal returnAmount;

  /**
   * 发货人
   */
  private String shippingName;

  /**
   * 发货人地址
   */
  private String shippingAddress;

  /**
   * 手机
   */
  private String mobile;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 单据类型
   */
  private String orderType;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 合计上架数量
   */
  private BigDecimal totalShelvedQuantity;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;

  /**
   * 快递类别
   */
  private Byte expressCorpType;

  /**
   * 快递ID
   */
  private Long expressCorpId;

  /**
   * 快递名称
   */
  private String expressCorpName;

  /**
   * 快递单号
   */
  private String expressCode;

  /**
   * 运费
   */
  private BigDecimal shippingAmount;

  /**
   * 发货方式
   */
  private String shippingMethod;

  /**
   * 配送类型
   */
  private String distributionType;

  /**
   * 线路名称
   */
  private String lineName;

  /**
   * 线路编号
   */
  private String lineCode;

  /**
   * 线路ID
   */
  private Long lineId;

  /**
   * 仓库编号
   */
  private String storageCode;


}

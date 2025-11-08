package com.yiruantong.outbound.domain.service;

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
 * 换货管理对象 out_exchange
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_exchange", autoResultMap = true)
public class OutExchange extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 换货单ID
   */
  @TableId(value = "exchange_id")
  private Long exchangeId;

  /**
   * 换货单号
   */
  private String exchangeCode;

  /**
   * 出库仓库ID
   */
  private Long storageId;

  /**
   * 出库仓库
   */
  private String storageName;

  /**
   * 入库仓库ID
   */
  private Long storageIdIn;

  /**
   * 入库仓库
   */
  private String storageNameIn;

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
   * 客户ID
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
   * 换货日期
   */
  private Date applyDate;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 入库合计数量
   */
  private BigDecimal totalEnterQuantity;

  /**
   * 入库合计金额
   */
  private BigDecimal totalEnterAmount;

  /**
   * 出库合计数量
   */
  private BigDecimal totalOuterQuantity;

  /**
   * 出库合计金额
   */
  private BigDecimal totalOuterAmount;

  /**
   * 换货差额
   */
  private BigDecimal differenceAmount;

  /**
   * 优惠额度
   */
  private BigDecimal favourAmount;

  /**
   * 优惠后金额
   */
  private BigDecimal factAmount;

  /**
   * 实收金额
   */
  private BigDecimal receiveAmount;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 换货状态
   */
  private String exchangeStatus;

  /**
   *
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
   *
   */
  private String consignorCode;

  /**
   *
   */
  private String consignorName;

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
   * 仓库编号
   */
  private String storageCode;


}

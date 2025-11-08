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
 * 出库退货单对象 out_return
 *
 * @author YiRuanTong
 * @date 2025-02-24
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "out_return", autoResultMap = true)
public class OutReturn extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 退货单ID
   */
  @TableId(value = "return_id")
  private Long returnId;

  /**
   * 退货单编号
   */
  private String returnCode;

  /**
   * 出库单ID
   */
  private Long orderId;

  /**
   * 出库单编号
   */
  private String orderCode;

  /**
   * 入库仓库ID
   */
  private Long storageId;

  /**
   * 入库仓库名称
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
   * 部门
   */
  private String deptName;

  /**
   * 入库日期
   */
  private Date applyDate;

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
   * 合计数量
   */
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 退货数量
   */
  private BigDecimal totalReturnQuantity;

  /**
   * 退货金额
   */
  private BigDecimal totalSaleAmount;

  /**
   * 已退金额
   */
  private BigDecimal totalRefundAmount;

  /**
   * 未退金额
   */
  private BigDecimal totalUnrefundAmount;

  /**
   * 已收金额
   */
  private BigDecimal totalPaid;

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
   * 退款状态
   */
  private String refundStatus;

  /**
   * 驾驶员姓名
   */
  private String driveName;

  /**
   * 车牌号
   */
  private String truckNo;

  /**
   * 物品重量
   */
  private BigDecimal weight;

  /**
   * 店铺订单编号
   */
  private String storeOrderCode;

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
   * 订单类型
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
   * 大单位数量
   */
  private BigDecimal bigOtyTotal;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 保修
   */
  private String guarantee;

  /**
   * 售后类型
   */
  private String returnType;

  /**
   * 问题归属
   */
  private String ascription;

  /**
   * 物流名称
   */
  private String expressCorpName;

  /**
   * 物流类别
   */
  private Byte expressCorpType;

  /**
   * 物流单号
   */
  private String expressCode;

  /**
   * 物流ID
   */
  private Long expressCorpId;

  /**
   * 仓库编号
   */
  private String storageCode;

  /**
   * 仓管状态
   */
  private String warehouseStatus;

  /**
   * 质检状态
   */
  private String checkingStatus;


}

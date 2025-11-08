package com.yiruantong.inbound.domain.in;

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
 * 入库管理对象 in_enter
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_enter", autoResultMap = true)
public class InEnter extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 入库单ID
   */
  @TableId(value = "enter_id")
  private Long enterId;

  /**
   * 入库单编号
   */
  private String enterCode;

  /**
   * 预到货单ID
   */
  private Long orderId;

  /**
   * 预到货单号
   */
  private String orderCode;

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
   * 入库日期
   */
  private Date applyDate;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

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
  private BigDecimal totalEnterQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 合计优惠金额
   */
  private BigDecimal totalFavourAmount;

  /**
   * 合计实付金额
   */
  private BigDecimal totalFactAmount;

  /**
   * 税率
   */
  private BigDecimal rate;

  /**
   * 合计价税
   */
  private BigDecimal totalRateAmount;

  /**
   * 付款期限
   */
  private Date payLimitDate;

  /**
   * 入库状态
   */
  private String enterStatus;

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
   * 已结算金额
   */
  private BigDecimal settleAmount;

  /**
   * 未已结算金额
   */
  private BigDecimal unSettleAmount;

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
   * 是否已打印
   */
  private Long isPrint;

  /**
   * 订单类型
   */
  private String orderType;

  /**
   * IPN编号
   */
  private String lpnCode;

  /**
   * 合计重量
   */
  private BigDecimal totalWeight;

  /**
   * 集装箱号
   */
  private String containerNos;

  /**
   * 装卸道口
   */
  private String dockCrossing;

  /**
   * 铅封号
   */
  private String sealNos;

  /**
   * 测温温度
   */
  private String surveyDegree;

  /**
   * 储存温度
   */
  private String storageDegree;

  /**
   * 是否使用装卸
   */
  private String isDock;

  /**
   * 异常
   */
  private String abnormal;

  /**
   * 异常件数
   */
  private String abnormalPieces;

  /**
   * 其他服务
   */
  private String otherServices;

  /**
   * 其他服务
   */
  private String truckNo;

  /**
   * 联系方式
   */
  private String mobile;

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
   * 跟踪单号
   */
  private String trackingNumber;

  /**
   * 扫描类型
   */
  private String scanInType;

  /**
   * 是否已上架
   */
  private String shelveStatus;

  /**
   * 上传文件
   */
  private String uploadFile;

  /**
   * 合计件数
   */
  private BigDecimal totalPackage;

  /**
   * 大单位数量
   */
  private BigDecimal bigQtyTotal;

  /**
   * 合计体积
   */
  private BigDecimal totalCube;

  /**
   * 费用项ID
   */
  private String feeItemIds;

  /**
   * 仓库编号
   */
  private String storageCode;


}

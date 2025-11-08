package com.yiruantong.inbound.domain.service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 换货单对象 in_exchange
 *
 * @author YiRuanTong
 * @date 2023-10-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_exchange", autoResultMap = true)
public class InExchange extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 换货单ID
   */
  @TableId(value = "exchange_id")
  private Long exchangeId;

  /**
   * 换货编号
   */
  private String exchangeCode;

  /**
   * 出货仓库ID
   */
  private Long storageId;

  /**
   * 出货仓库名称
   */
  private String storageName;

  /**
   * 入货仓库ID
   */
  private Long storageIdIn;

  /**
   * 入货仓库名称
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
   * 部门名称额
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
   * 税率
   */
  private BigDecimal rate;

  /**
   * 出库合计数量
   */
  private BigDecimal totalOuterQuantity;

  /**
   * 出库合计金额
   */
  private BigDecimal totalOuterAmount;

  /**
   * 入库合计数量
   */
  private BigDecimal totalEnterQuantity;

  /**
   * 入库合计金额
   */
  private BigDecimal totalEnterAmount;

  /**
   * 换货差额
   */
  private BigDecimal differenceAmount;

  /**
   * 优惠额度
   */
  private BigDecimal favourAmount;

  /**
   * 实付金额
   */
  private BigDecimal factAmount;

  /**
   * 支付金额
   */
  private BigDecimal payAmount;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

  /**
   * 分拣日期
   */
  private Date sortingDate;

  /**
   * 状态
   */
  private String exchangeStatus;

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


}

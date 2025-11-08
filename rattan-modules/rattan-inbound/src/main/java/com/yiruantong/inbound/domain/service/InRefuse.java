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
 * 拒收单对象 in_refuse
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_refuse", autoResultMap = true)
public class InRefuse extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 拒收单Id
   */
  @TableId(value = "refuse_id")
  private Long refuseId;

  /**
   * 拒收单编号
   */
  private String refuseCode;

  /**
   * 采购单Id
   */
  private Long orderId;

  /**
   * 采购单编号
   */
  private String orderCode;

  /**
   * 状态
   */
  private String refuseStatus;

  /**
   * 拒收数量
   */
  private Long refuseQuantity;

  /**
   * 拒收金额
   */
  private Long refusePurchaseAmount;

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
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商名称
   */
  private String providerShortName;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核状态
   */
  private Long auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

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


}

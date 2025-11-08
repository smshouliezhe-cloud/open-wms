package com.yiruantong.basic.domain.product;

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
 * 供应商合同管理对象 base_provider_contract
 *
 * @author YiRuanTong
 * @date 2024-05-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_provider_contract", autoResultMap = true)
public class BaseProviderContract extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 合同ID
   */
  @TableId(value = "contract_id")
  private Long contractId;

  /**
   * 合同单号
   */
  private String contractCode;

  /**
   * 供应商ID
   */
  private Long providerId;

  /**
   * 供应商编号
   */
  private String providerCode;

  /**
   * 供应商全称
   */
  private String providerName;

  /**
   * 供应商简称
   */
  private String providerShortName;

  /**
   * 货主id
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
   * 合同金额
   */
  private String contractPrice;

  /**
   * 账期开始日期
   */
  private Date accountStartDate;

  /**
   * 账期结束日期
   */
  private Date accountEndDate;

  /**
   * 开票周期开始日期
   */
  private Date billingStartDate;

  /**
   * 开票周期结束日期
   */
  private Date billingEndDate;

  /**
   * 付款周期开始日期
   */
  private Date paymentStartDate;

  /**
   * 付款周期结束日期
   */
  private Date paymentEndDate;

  /**
   * 责任人
   */
  private String responsiblePerson;

  /**
   * 发票名称
   */
  private String invoiceName;

  /**
   * 收件人
   */
  private String receivablesName;

  /**
   * 月付方式
   */
  private String monthWay;

  /**
   * 发票目录
   */
  private String invoiceCategory;

  /**
   * 发票类型
   */
  private String invoiceType;

  /**
   * 税点
   */
  private BigDecimal taxPoint;

  /**
   * 合同类型
   */
  private String contractType;

  /**
   * 合同开始日期
   */
  private Date contractStart;

  /**
   * 合同结束日期
   */
  private Date contractEnd;

  /**
   * 报价
   */
  private String quotation;

  /**
   * 发票信息
   */
  private String invoiceInfo;

  /**
   * 自定义编号
   */
  private String customNo;

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


}

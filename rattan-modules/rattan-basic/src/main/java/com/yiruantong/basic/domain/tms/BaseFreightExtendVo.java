package com.yiruantong.basic.domain.tms;

import lombok.Data;

import java.io.Serial;
import java.util.Date;

@Data
public class BaseFreightExtendVo {
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 模版Id
   */
  private Long templateId;

  /**
   * 模板编号
   */
  private String templateCode;

  /**
   * 模板名称
   */
  private String templateName;

  /**
   * 开始时间
   */
  private Date beginTime;

  /**
   * 结束时间
   */
  private Date endTime;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 物流公司ID
   */
  private Long expressCorpId;

  /**
   * 物流公司
   */
  private String expressCorpName;

  /**
   * 币种
   */
  private String currencyCode;

  /**
   * 单据状态
   */
  private String statusText;

  /**
   * 运输方式
   */
  private String transportMode;

  /**
   * 运费计算
   */
  private String priceJson;

  /**
   * 是否可用
   */
  private String enable;

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
   * 审核备注
   */
  private String auditRemark;

  /**
   * 模板类型
   */
  private String templateType;

  /**
   * 价格设置Json
   */
  private String chargeData;

  /**
   * 承运商ID
   */
  private Long carrierId;

  /**
   * 承运商
   */
  private String carrierName;

  /**
   * 运价方式
   */
  private String pricingManner;

  /**
   * 物流方式
   */
  private String logisticsMode;

  /**
   * 运价类型
   */
  private String freightType;

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
   * 客户ID
   */
  private Long clientId;

  /**
   * 客户编号
   */
  private String clientCode;

  /**
   * 客户简称
   */
  private String clientShortName;

  /**
   * 费用科目id
   */
  private Long feeItemId;

  /**
   * 费用科目
   */
  private String feeItemName;

  /**
   * 自动生成运费
   */
  private Byte autoBuildFreight;

  /**
   * 费用类型
   */
  private String chargeType;


}

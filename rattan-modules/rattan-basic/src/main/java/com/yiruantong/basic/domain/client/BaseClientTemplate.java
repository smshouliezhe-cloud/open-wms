package com.yiruantong.basic.domain.client;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 客户运价模板对象 base_client_template
 *
 * @author YRT
 * @date 2024-04-12
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_client_template", autoResultMap = true)
public class BaseClientTemplate extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 模板ID
   */
  @TableId(value = "template_id")
  private Long templateId;

  /**
   * 客户ID
   */
  private Long clientId;

  /**
   * 费用模块
   */
  private String costModule;

  /**
   * 费用科目id
   */
  private Long feeItemId;

  /**
   * 费用科目
   */
  private String feeItemName;

  /**
   * 计价方式
   */
  private String pricingManner;

  /**
   * 模版类型
   */
  private String templateType;

  /**
   * 自动生成运费
   */
  private Long autoBuildFreight;

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

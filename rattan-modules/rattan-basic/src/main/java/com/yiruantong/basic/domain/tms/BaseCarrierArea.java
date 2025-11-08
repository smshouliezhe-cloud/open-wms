package com.yiruantong.basic.domain.tms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 承运商管辖区域对象 base_carrier_area
 *
 * @author YRT
 * @date 2025-02-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_carrier_area", autoResultMap = true)
public class BaseCarrierArea extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 明细id
   */
  @TableId(value = "carrier_area_id")
  private Long carrierAreaId;

  /**
   * 承运商ID
   */
  private Long carrierId;

  /**
   * 省ID
   */
  private Long provinceId;

  /**
   * 省
   */
  private String provinceName;

  /**
   * 市ID
   */
  private Long cityId;

  /**
   * 市
   */
  private String cityName;

  /**
   * 区ID
   */
  private Long regionId;

  /**
   * 区
   */
  private String regionName;

  /**
   * 计价方式
   */
  private String pricingManner;

  /**
   * 费用科目
   */
  private String feeItemName;

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
   * 排序号
   */
  private Long orderNum;

  /**
   * 是否可用
   */
  private Long enable;
  /**
   * 目的地网点
   */
  private String unloadSite;

  /**
   * 温层
   */
  private String thermocLine;
}

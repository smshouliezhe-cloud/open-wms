package com.yiruantong.inbound.domain.in;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 商品上架明细的明细对象 in_shelve_detail_step
 *
 * @author YiRuanTong
 * @date 2025-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "in_shelve_detail_step", autoResultMap = true)
public class InShelveDetailStep extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 上架明细ID
   */
  @TableId(value = "shelve_detail_step_id")
  private Long shelveDetailStepId;

  /**
   * 明细ID
   */
  private Long shelveDetailId;

  /**
   * 上架ID
   */
  private Long shelveId;

  /**
   * 入库单明细ID
   */
  private Long enterDetailId;

  /**
   * 入库单ID
   */
  private Long enterId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 产品名称
   */
  private String productName;

  /**
   * 扫描数量
   */
  private BigDecimal quantity;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 上架状态
   */
  private String shelveStatus;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

  /**
   * 小计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * SN码
   */
  private String singleSignCode;

  /**
   * 生产日期
   */
  private Date produceDate;

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

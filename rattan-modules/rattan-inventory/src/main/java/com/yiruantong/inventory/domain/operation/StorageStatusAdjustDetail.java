package com.yiruantong.inventory.domain.operation;

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
 * 状态属性调整明细对象 storage_status_adjust_detail
 *
 * @author YRT
 * @date 2025-02-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_status_adjust_detail", autoResultMap = true)
public class StorageStatusAdjustDetail extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 调整ID
   */
    @TableId(value = "status_adjust_detail_id")
  private Long statusAdjustDetailId;

  /**
   * 调整ID
   */
  private Long statusAdjustId;

  /**
   * 库存ID
   */
  private Long inventoryId;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 库存量
   */
  private BigDecimal productStorage;

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
   * 条形码
   */
  private String productModel;

  /**
   * 产品规格
   */
  private String productSpec;

  /**
   * 批次号
   */
  private String batchNumber;

  /**
   * 保质期天数
   */
  private Long shelfLifeDay;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 到期日期
   */
  private Date limitDate;

  /**
   * 原属性
   */
  private String productAttribute;

  /**
   * 原状态
   */
  private String storageStatus;

  /**
   * 目标属性
   */
  private String productAttributeTarget;

  /**
   * 目标状态
   */
  private String storageStatusTarget;

  /**
   * 合计毛重
   */
  private BigDecimal rowWeight;

  /**
   * 单位毛重
   */
  private BigDecimal weight;

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
   * 单位净重
   */
  private BigDecimal netWeight;

  /**
   * 小计净重
   */
  private BigDecimal rowNetWeight;

  /**
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源主表ID
   */
  private String sourceMainId;

  /**
   * 来源明细ID
   */
  private String sourceDetailId;

  /**
   * 项目号
   */
  private String projectCode;

  /**
   * 箱号
   */
  private String caseNumber;

  /**
   * 分拣状态
   */
  private Long sortingStatus;

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
   * 订单行号
   */
  private String orderLineCode;

  /**
   * 异议号
   */
  private String objectionCode;

  /**
   * 缺货数量
   */
  private BigDecimal lackStorage;


}

package com.yiruantong.inventory.domain.process;

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
 * 加工列明细对象 process_order_detail
 *
 * @author YRT
 * @date 2025-01-17
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "process_order_detail", autoResultMap = true)
public class ProcessOrderDetail extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 明细id
   */
    @TableId(value = "process_list_id")
  private Long processListId;

  /**
   * 主表id
   */
  private Long processId;

  /**
   * 来源id
   */
  private Long billId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 出货仓库ID
   */
  private Long storageId;

  /**
   * 出货仓库
   */
  private String storageName;

  /**
   * 平台主表id
   */
  private Long sourceMainId;

  /**
   * 平台明细id
   */
  private Long sourceListId;

  /**
   * 所属仓位
   */
  private String areaId;

  /**
   * 投料重量
   */
  private BigDecimal feedingWeight;

  /**
   * 投料重量单位
   */
  private String feedingWeightUnit;

  /**
   * 投料数量
   */
  private BigDecimal feedingNumber;

  /**
   * 投料数量单位
   */
  private String feedingNumberUnit;

  /**
   * 产出重量
   */
  private BigDecimal outputWeight;

  /**
   * 产出重量单位
   */
  private String outputWeightUnit;

  /**
   * 产出件数
   */
  private Long outputNumber;

  /**
   * 产出件数单位
   */
  private String outputNumberUnit;

  /**
   * 出成率
   */
  private String yieId;

  /**
   * 状态
   */
  private String orderStatus;

  /**
   * 仓位
   */
  private String areaName;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

  /**
   * 扩展字段
   */
    @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

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

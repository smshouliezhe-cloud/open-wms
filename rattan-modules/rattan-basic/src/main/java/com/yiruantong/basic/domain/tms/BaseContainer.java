package com.yiruantong.basic.domain.tms;

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
 * 集装箱信息对象 base_container
 *
 * @author YRT
 * @date 2025-01-21
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_container", autoResultMap = true)
public class BaseContainer extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 集装箱ID
   */
    @TableId(value = "container_id")
  private Long containerId;

  /**
   * 集装箱号
   */
  private String containerNo;

  /**
   * 集装箱类型
   */
  private String containerType;

  /**
   * 尺寸
   */
  private String size;

  /**
   * 交易编号
   */
  private String containerSpellMode;

  /**
   * 铅封号
   */
  private String sealNo;

  /**
   * 预订舱长宽高(mm)
   */
  private String cabinLwhBooking;

  /**
   * 实际订舱长宽高(mm)
   */
  private String cabinLwhActual;

  /**
   * 装箱后实际长宽高(mm)
   */
  private String cabinLwhAfter;

  /**
   * 体积
   */
  private BigDecimal volume;

  /**
   * 自重(KGS)
   */
  private BigDecimal weight;

  /**
   * 运输ID
   */
  private Long transportId;

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
   * 运输类型
   */
  private String transportationType;

}

package com.yiruantong.basic.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.TenantEntity;

import java.io.Serial;
import java.util.Date;
import java.util.Map;

/**
 * 门店销售等级设置对象 base_consignor_sales_level
 *
 * @author YRT
 * @date 2025-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "base_consignor_sales_level", autoResultMap = true)
public class BaseConsignorSalesLevel extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 销售等级id
   */
  @TableId(value = "sales_level_id")
  private Long salesLevelId;

  /**
   * 销售等级S
   */
  private String salesLevelS;

  /**
   * 销售等级A
   */
  private String salesLevelA;

  /**
   * 销售等级B
   */
  private String salesLevelB;

  /**
   * 销售等级C
   */
  private String salesLevelC;

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

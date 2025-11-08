package com.yiruantong.inventory.domain.core;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;


import java.io.Serial;

/**
 * 分拣规则对象 core_sorting_rule
 *
 * @author YRT
 * @date 2025-02-27
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "core_sorting_rule", autoResultMap = true)
public class CoreSortingRule extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 分拣规则ID
   */
    @TableId(value = "sorting_rule_id")
  private Long sortingRuleId;

  /**
   * 单价类型
   */
  private String billType;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 单据号
   */
  private String billCode;

  /**
   * 单据明细ID
   */
  private Long billDetailId;

  /**
   * 商品ID
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
   * 规则类型
   */
  private String sortingType;

  /**
   * 分拣规则
   */
    @TableField(value = "sorting_rule", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> sortingRule;

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


}

package com.yiruantong.inventory.domain.allocate;

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
 * 调拨单设置规则对象 apply_sorting_rule
 *
 * @author YRT
 * @date 2024-01-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "apply_sorting_rule", autoResultMap = true)
public class ApplySortingRule extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "rule_id")
  private Long ruleId;

  /**
   * 订单编号
   */
  private String allocateApplyCode;

  /**
   * 产品编号
   */
  private String productCode;

  /**
   * 货位名称
   */
  private String positionName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 货主ID
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
   * 批次号
   */
  private String batchNumber;

  /**
   * 生产日期
   */
  private Date produceDate;

  /**
   * 托盘号
   */
  private String plateCode;

  /**
   * SN号
   */
  private String singleSignCode;

  /**
   * 订单ID
   */
  private Long allocateApplyId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 明细ID
   */
  private Long allocateApplyDetailId;

  /**
   * 库存ID
   */
  private Long inventoryId;

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

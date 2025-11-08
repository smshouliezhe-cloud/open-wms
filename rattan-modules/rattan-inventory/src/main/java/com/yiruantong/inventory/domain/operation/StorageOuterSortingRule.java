package com.yiruantong.inventory.domain.operation;

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
 * 其他出库规则对象 storage_outer_sorting_rule
 *
 * @author YRT
 * @date 2023-12-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_outer_sorting_rule", autoResultMap = true)
public class StorageOuterSortingRule extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "rule_id")
  private Long ruleId;

  /**
   * 其他出库单号
   */
  private String outerCode;

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
   * 其他出库单ID
   */
  private Long outerId;

  /**
   * 产品ID
   */
  private Long productId;

  /**
   * 销售明细ID
   */
  private Long orderDetailId;

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

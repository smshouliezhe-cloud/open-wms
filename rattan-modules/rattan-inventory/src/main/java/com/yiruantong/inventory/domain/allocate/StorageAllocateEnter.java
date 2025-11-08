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
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 调拨入库单对象 storage_allocate_enter
 *
 * @author YRT
 * @date 2023-12-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "storage_allocate_enter", autoResultMap = true)
public class StorageAllocateEnter extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 调拨单ID
   */
  @TableId(value = "allocate_enter_id")
  private Long allocateEnterId;

  /**
   * 调拨单编号
   */
  private String allocateEnterCode;

  /**
   * 调拨申请单ID
   */
  private Long allocateApplyId;

  /**
   * 调拨申请单编号
   */
  private String allocateApplyCode;

  /**
   * 经手人ID
   */
  private Long userId;

  /**
   * 经手人
   */
  private String nickName;

  /**
   * 部门ID
   */
  private Long deptId;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 入库日期
   */
  private Date enterDate;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 入库名称
   */
  private String storageName;

  /**
   * 调入仓库ID
   */
  private Long storageIdIn;

  /**
   * 调入仓库名称
   */
  private String storageNameIn;

  /**
   * 合计数量
   */
  private BigDecimal enterQuantity;

  /**
   * 合计金额
   */
  private BigDecimal totalAmount;

  /**
   * 调拨状态
   */
  private String enterStatus;

  /**
   * 审核人
   */
  private String auditor;

  /**
   * 审核
   */
  private Byte auditing;

  /**
   * 审核日期
   */
  private Date auditDate;

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
   * 单据类型
   */
  private String orderType;

  /**
   * 毛重合计
   */
  private BigDecimal totalWeight;

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
   * 合计净重
   */
  private BigDecimal totalNetWeight;

  /**
   * 含税金额
   */
  private BigDecimal taxAmount;


}

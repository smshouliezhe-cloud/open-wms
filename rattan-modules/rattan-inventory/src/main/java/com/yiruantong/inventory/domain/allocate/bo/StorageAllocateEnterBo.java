package com.yiruantong.inventory.domain.allocate.bo;

import com.yiruantong.inventory.domain.allocate.StorageAllocateEnter;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 调拨入库单业务对象 storage_allocate_enter
 *
 * @author YRT
 * @date 2023-12-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageAllocateEnter.class, reverseConvertGenerate = false)
public class StorageAllocateEnterBo extends BaseEntity {

  /**
   * 调拨单ID
   */
  @NotNull(message = "调拨单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long allocateEnterId;

  /**
   * 调拨单编号
   */
  @NotBlank(message = "调拨单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String allocateEnterCode;

  /**
   * 调拨申请单ID
   */
  @NotNull(message = "调拨申请单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long allocateApplyId;

  /**
   * 调拨申请单编号
   */
  @NotBlank(message = "调拨申请单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String allocateApplyCode;

  /**
   * 经手人ID
   */
  @NotNull(message = "经手人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 经手人
   */
  @NotBlank(message = "经手人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nickName;

  /**
   * 部门ID
   */
  @NotNull(message = "部门ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 入库日期
   */
  @NotNull(message = "入库日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date enterDate;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 入库名称
   */
  @NotBlank(message = "入库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 调入仓库ID
   */
  @NotNull(message = "调入仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageIdIn;

  /**
   * 调入仓库名称
   */
  @NotBlank(message = "调入仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageNameIn;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal enterQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 调拨状态
   */
  @NotBlank(message = "调拨状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String enterStatus;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 货主ID
   */
  @NotNull(message = "货主ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long consignorId;

  /**
   * 货主编号
   */
  @NotBlank(message = "货主编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorCode;

  /**
   * 货主名称
   */
  @NotBlank(message = "货主名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String consignorName;

  /**
   * 单据类型
   */
  @NotBlank(message = "单据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String orderType;

  /**
   * 毛重合计
   */
  @NotNull(message = "毛重合计不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 来源类别
   */
  @NotBlank(message = "来源类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceType;

  /**
   * 来源ID
   */
  @NotBlank(message = "来源ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 合计净重
   */
  @NotNull(message = "合计净重不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalNetWeight;

  /**
   * 含税金额
   */
  @NotNull(message = "含税金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal taxAmount;


}

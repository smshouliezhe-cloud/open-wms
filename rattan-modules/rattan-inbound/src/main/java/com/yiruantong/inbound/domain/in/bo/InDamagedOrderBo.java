package com.yiruantong.inbound.domain.in.bo;

import com.yiruantong.inbound.domain.in.InDamagedOrder;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 残品入库单业务对象 in_damaged_order
 *
 * @author YiRuanTong
 * @date 2025-02-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = InDamagedOrder.class, reverseConvertGenerate = false)
public class InDamagedOrderBo extends BaseEntity {

  /**
   * 残品单ID
   */
  @NotNull(message = "残品单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long damagedOrderId;

  /**
   * 残品单编号
   */
  @NotBlank(message = "残品单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String damagedOrderCode;

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
   * 默认供应商ID
   */
  @NotNull(message = "默认供应商ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long providerId;

  /**
   * 默认供应商编号
   */
  @NotBlank(message = "默认供应商编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerCode;

  /**
   * 默认供应商名称
   */
  @NotBlank(message = "默认供应商名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String providerShortName;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

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
   * 报残日期
   */
  @NotNull(message = "报残日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date damagedData;

  /**
   * 残品入库状态
   */
  @NotBlank(message = "残品入库状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String damagedStatus;

  /**
   * 合计数量
   */
  @NotNull(message = "合计数量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalQuantity;

  /**
   * 合计金额
   */
  @NotNull(message = "合计金额不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalAmount;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核
   */
  @NotNull(message = "审核不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核日期
   */
  @NotNull(message = "审核日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

  /**
   * 可转为预到货单
   */
  @NotNull(message = "可转为预到货单不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isTransferOrder;

  /**
   * 合计重量
   */
  @NotNull(message = "合计重量不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalWeight;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
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
   * 仓库编号
   */
  @NotBlank(message = "仓库编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageCode;


}

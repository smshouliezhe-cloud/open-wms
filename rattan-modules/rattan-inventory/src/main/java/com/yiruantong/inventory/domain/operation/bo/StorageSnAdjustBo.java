package com.yiruantong.inventory.domain.operation.bo;

import com.yiruantong.inventory.domain.operation.StorageSnAdjust;
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
 * SN调整业务对象 storage_sn_adjust
 *
 * @author YRT
 * @date 2024-09-05
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = StorageSnAdjust.class, reverseConvertGenerate = false)
public class StorageSnAdjustBo extends BaseEntity {

  /**
   * SN调整ID
   */
  @NotNull(message = "SN调整ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long snAdjustId;

  /**
   * SN调整编号
   */
  @NotBlank(message = "SN调整编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String snAdjustCode;

  /**
   * 调整状态
   */
  @NotBlank(message = "调整状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String adjustStatus;

  /**
   * 调整日期
   */
  @NotNull(message = "调整日期不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date adjustDate;

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
   * 审核备注
   */
  @NotBlank(message = "审核备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditRemark;

  /**
   * 合计SN数
   */
  @NotNull(message = "合计SN数不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal totalSnCount;

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


}

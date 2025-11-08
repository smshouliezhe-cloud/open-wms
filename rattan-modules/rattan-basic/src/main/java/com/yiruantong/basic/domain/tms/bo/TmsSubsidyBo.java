package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsSubsidy;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * 挂车管理业务对象 tms_subsidy
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsSubsidy.class, reverseConvertGenerate = false)
public class TmsSubsidyBo extends BaseEntity {

  /**
   * 车贴ID
   */
  @NotNull(message = "车贴ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long subsidyId;

  /**
   * 车贴单号
   */
  @NotBlank(message = "车贴单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subsidyCode;

  /**
   * 司机姓名
   */
  @NotBlank(message = "司机姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 手机号
   */
  @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 生成状态
   */
  @NotBlank(message = "生成状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String buildStatus;

  /**
   * 车贴奖励
   */
  @NotNull(message = "车贴奖励不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long subsidyReward;

  /**
   * 结算对象
   */
  @NotBlank(message = "结算对象不能为空", groups = {AddGroup.class, EditGroup.class})
  private String settlementName;

  /**
   * 审核状态
   */
  @NotNull(message = "审核状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long auditing;

  /**
   * 审核人
   */
  @NotBlank(message = "审核人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String auditor;

  /**
   * 审核时间
   */
  @NotNull(message = "审核时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date auditDate;

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


}

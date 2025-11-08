package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsFeedback;
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
 * 司机反馈业务对象 tms_feedback
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsFeedback.class, reverseConvertGenerate = false)
public class TmsFeedbackBo extends BaseEntity {

  /**
   * 反馈ID
   */
  @NotNull(message = "反馈ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long feedbackId;

  /**
   * 司机姓名
   */
  @NotBlank(message = "司机姓名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String driverName;

  /**
   * 手机号码
   */
  @NotBlank(message = "手机号码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String mobile;

  /**
   * 车牌号
   */
  @NotBlank(message = "车牌号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String truckNo;

  /**
   * 问题类型
   */
  @NotBlank(message = "问题类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String questionType;

  /**
   * 处理类型
   */
  @NotBlank(message = "处理类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String resolutionType;

  /**
   * 反馈内容
   */
  @NotBlank(message = "反馈内容不能为空", groups = {AddGroup.class, EditGroup.class})
  private String feedbackContent;

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

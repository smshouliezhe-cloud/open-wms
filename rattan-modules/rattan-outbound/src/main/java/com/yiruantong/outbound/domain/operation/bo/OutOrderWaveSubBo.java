package com.yiruantong.outbound.domain.operation.bo;

import com.yiruantong.outbound.domain.operation.OutOrderWaveSub;
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
 * 子波次业务对象 out_order_wave_sub
 *
 * @author YRT
 * @date 2024-09-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OutOrderWaveSub.class, reverseConvertGenerate = false)
public class OutOrderWaveSubBo extends BaseEntity {

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long subId;

  /**
   * 子波次单号
   */
  @NotBlank(message = "子波次单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subOrderWaveCode;

  /**
   * 波次ID
   */
  @NotNull(message = "波次ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderWaveId;

  /**
   * 库区
   */
  @NotBlank(message = "库区不能为空", groups = {AddGroup.class, EditGroup.class})
  private String areaCode;

  /**
   * 子波次状态
   */
  @NotBlank(message = "子波次状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subWaveStatus;

  /**
   * 拣货人ID
   */
  @NotNull(message = "拣货人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long pickUserId;

  /**
   * 拣货人
   */
  @NotBlank(message = "拣货人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pickNickName;

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

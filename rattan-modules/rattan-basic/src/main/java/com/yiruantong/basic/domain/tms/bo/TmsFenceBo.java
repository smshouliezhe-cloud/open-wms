package com.yiruantong.basic.domain.tms.bo;

import com.yiruantong.basic.domain.tms.TmsFence;
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
 * 围栏管理业务对象 tms_fence
 *
 * @author YRT
 * @date 2023-11-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsFence.class, reverseConvertGenerate = false)
public class TmsFenceBo extends BaseEntity {

  /**
   * 电子围栏ID
   */
  @NotNull(message = "电子围栏ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long fenceId;

  /**
   * 电子围栏编号
   */
  @NotBlank(message = "电子围栏编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fenceCode;

  /**
   * 电子围栏名称
   */
  @NotBlank(message = "电子围栏名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fenceName;

  /**
   * 半径
   */
  @NotNull(message = "半径不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long radius;

  /**
   * 状态
   */
  @NotBlank(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String statusText;

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

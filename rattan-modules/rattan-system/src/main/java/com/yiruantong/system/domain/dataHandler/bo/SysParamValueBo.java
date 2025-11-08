package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysParamValue;
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


/**
 * 下拉框值设置业务对象 sys_param_value
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysParamValue.class, reverseConvertGenerate = false)
public class SysParamValueBo extends BaseEntity {

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long paramId;

  /**
   * 参数类别ID
   */
  @NotNull(message = "参数类别ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long typeId;

  /**
   * 参数ID
   */
  @NotNull(message = "参数ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long value01;

  /**
   * 参数值1
   */
  @NotBlank(message = "参数值1不能为空", groups = {AddGroup.class, EditGroup.class})
  private String value02;

  /**
   * 参数值2
   */
  @NotBlank(message = "参数值2不能为空", groups = {AddGroup.class, EditGroup.class})
  private String value03;

  /**
   * 参数值3
   */
  @NotBlank(message = "参数值3不能为空", groups = {AddGroup.class, EditGroup.class})
  private String value04;

  /**
   * 参数值4
   */
  @NotBlank(message = "参数值4不能为空", groups = {AddGroup.class, EditGroup.class})
  private String value05;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

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

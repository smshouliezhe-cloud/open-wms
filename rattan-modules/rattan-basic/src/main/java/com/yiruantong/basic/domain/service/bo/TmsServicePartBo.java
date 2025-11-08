package com.yiruantong.basic.domain.service.bo;

import com.yiruantong.basic.domain.service.TmsServicePart;
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
 * 维修配件管理业务对象 tms_service_part
 *
 * @author YRT
 * @date 2024-03-09
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TmsServicePart.class, reverseConvertGenerate = false)
public class TmsServicePartBo extends BaseEntity {

  /**
   * 配件ID
   */
  @NotNull(message = "配件ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long servicePartId;

  /**
   * 配件编号
   */
  @NotBlank(message = "配件编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String partCode;

  /**
   * 配件名称
   */
  @NotBlank(message = "配件名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String partName;

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

package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysExportVueData;
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
 * 导出视图设置业务对象 sys_export_vue_data
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysExportVueData.class, reverseConvertGenerate = false)
public class SysExportVueDataBo extends BaseEntity {

  /**
   * 导出视图ID
   */
  @NotNull(message = "导出视图ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long vueDataId;

  /**
   * 导出信息ID
   */
  @NotNull(message = "导出信息ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long exportId;

  /**
   * 导出名称
   */
  @NotBlank(message = "导出名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueDataName;

  /**
   * 导出JSON
   */
  @NotBlank(message = "导出JSON不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueData;

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
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

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

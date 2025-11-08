package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysExport;
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
 * 导出设置业务对象 sys_export
 *
 * @author YRT
 * @date 2024-07-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysExport.class, reverseConvertGenerate = false)
public class SysExportBo extends BaseEntity {

  /**
   * 导出ID
   */
  @NotNull(message = "导出ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long exportId;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 表名称
   */
  @NotBlank(message = "表名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tableName;

  /**
   * 表中文名
   */
  @NotBlank(message = "表中文名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tableNameCn;

  /**
   * 模板路径
   */
  @NotBlank(message = "模板路径不能为空", groups = {AddGroup.class, EditGroup.class})
  private String templatePath;

  /**
   * 导出类型
   */
  @NotBlank(message = "导出类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String exportType;

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

  /**
   * 自定义导出ID
   */
  @NotNull(message = "自定义导出ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long customExportId;


}

package com.yiruantong.system.domain.dataHandler.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.system.domain.dataHandler.SysExportColumn;

import java.util.Date;
import java.util.Map;


/**
 * 导出字段业务对象 sys_export_column
 *
 * @author YRT
 * @date 2024-05-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysExportColumn.class, reverseConvertGenerate = false)
public class SysExportColumnBo extends BaseEntity {

  /**
   * 导入字段ID
   */
  @NotNull(message = "导入字段ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long columnId;

  /**
   * 导入信息ID
   */
  @NotNull(message = "导入信息ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long exportId;

  /**
   * 字段名
   */
  @NotBlank(message = "字段名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String columnName;

  /**
   * 字段中文名
   */
  @NotBlank(message = "字段中文名不能为空", groups = {AddGroup.class, EditGroup.class})
  private String cnName;

  /**
   * 字段表达式
   */
  @NotBlank(message = "字段表达式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String colExpression;

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
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

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
   * 数据类型
   */
  @NotBlank(message = "数据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String dataType;

  /**
   * 格式化模板
   */
  @NotBlank(message = "格式化模板不能为空", groups = {AddGroup.class, EditGroup.class})
  private String dataFormatter;

  /**
   * 开启扩展字段
   */
  @NotBlank(message = "开启扩展字段", groups = {AddGroup.class, EditGroup.class})
  private Byte isExpandField;
}

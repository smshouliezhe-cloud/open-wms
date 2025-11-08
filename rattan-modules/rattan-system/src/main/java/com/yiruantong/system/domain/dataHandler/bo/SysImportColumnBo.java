package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysImportColumn;
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
 * 导入字段业务对象 sys_import_column
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysImportColumn.class, reverseConvertGenerate = false)
public class SysImportColumnBo extends BaseEntity {

  /**
   * 导入字段ID
   */
  @NotNull(message = "导入字段ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long columnId;

  /**
   * 导入信息ID
   */
  @NotNull(message = "导入信息ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long importId;

  /**
   * 字段类型
   */
  @NotBlank(message = "字段类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String columnType;

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
   * 验证类型
   */
  @NotBlank(message = "验证类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String validate;

  /**
   * 验证规则
   */
  @NotBlank(message = "验证规则不能为空", groups = {AddGroup.class, EditGroup.class})
  private String valExpression;

  /**
   * 验证描述
   */
  @NotBlank(message = "验证描述不能为空", groups = {AddGroup.class, EditGroup.class})
  private String valDescription;

  /**
   * 是否必填
   */
  @NotNull(message = "是否必填不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isMust;

  /**
   * 导入模式
   */
  @NotNull(message = "导入模式不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte importMode;

  /**
   * 表达式
   */
  @NotBlank(message = "表达式不能为空", groups = {AddGroup.class, EditGroup.class})
  private String express;

  /**
   * 是否扩展字段
   */
  @NotNull(message = "是否扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isExpandField;

  /**
   * 带入字段
   */
  @NotBlank(message = "带入字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private String bringField;

  /**
   * 是否分组
   */
  @NotNull(message = "是否分组不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isGroup;

  /**
   * 数据类型
   */
  @NotBlank(message = "数据类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String dataType;

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

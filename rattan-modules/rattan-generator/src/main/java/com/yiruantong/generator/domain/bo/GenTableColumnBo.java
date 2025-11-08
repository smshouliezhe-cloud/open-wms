package com.yiruantong.generator.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.generator.domain.GenTableColumn;

/**
 * 代码生成业务字段业务对象 gen_table_column
 *
 * @author YRT
 * @date 2023-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = GenTableColumn.class, reverseConvertGenerate = false)
public class GenTableColumnBo extends BaseEntity {

  /**
   * 编号
   */
  @NotNull(
    message = "编号不能为空",
    groups = {EditGroup.class})
  private Long columnId;

  /**
   * 归属表编号
   */
  @NotNull(
    message = "归属表编号不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private Long tableId;

  /**
   * 列名称
   */
  @NotBlank(
    message = "列名称不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String columnName;

  /**
   * 列描述
   */
  @NotBlank(
    message = "列描述不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String columnComment;

  /**
   * 列类型
   */
  @NotBlank(
    message = "列类型不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String columnType;

  /**
   * JAVA类型
   */
  @NotBlank(
    message = "JAVA类型不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String javaType;

  /**
   * JAVA字段名
   */
  @NotBlank(
    message = "JAVA字段名不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String javaField;

  /**
   * 是否主键（1是）
   */
  @NotBlank(
    message = "是否主键（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isPk;

  /**
   * 是否自增（1是）
   */
  @NotBlank(
    message = "是否自增（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isIncrement;

  /**
   * 是否必填（1是）
   */
  @NotBlank(
    message = "是否必填（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isRequired;

  /**
   * 是否为插入字段（1是）
   */
  @NotBlank(
    message = "是否为插入字段（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isInsert;

  /**
   * 是否编辑字段（1是）
   */
  @NotBlank(
    message = "是否编辑字段（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isEdit;

  /**
   * 是否列表字段（1是）
   */
  @NotBlank(
    message = "是否列表字段（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isList;

  /**
   * 是否查询字段（1是）
   */
  @NotBlank(
    message = "是否查询字段（1是）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String isQuery;

  /**
   * 查询方式（等于、不等于、大于、小于、范围）
   */
  @NotBlank(
    message = "查询方式（等于、不等于、大于、小于、范围）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String queryType;

  /**
   * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
   */
  @NotBlank(
    message = "显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String htmlType;

  /**
   * 字典类型
   */
  @NotBlank(
    message = "字典类型不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String dictType;

  /**
   * 排序
   */
  @NotNull(
    message = "排序不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private Long ordeNum;

  /**
   * 删除人id
   */
  @NotNull(
    message = "删除人id不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(
    message = "删除人不能为空",
    groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * do注解
   */
  private String doAnnotation;

  /**
   * vo注解
   */
  private String voAnnotation;

  /**
   * bo注解
   */
  private String boAnnotation;
}

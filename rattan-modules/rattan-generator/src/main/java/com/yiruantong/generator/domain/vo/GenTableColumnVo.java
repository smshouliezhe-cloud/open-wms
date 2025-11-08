package com.yiruantong.generator.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import com.yiruantong.generator.domain.GenTableColumn;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 代码生成业务字段视图对象 gen_table_column
 *
 * @author YRT
 * @date 2023-08-12
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = GenTableColumn.class)
public class GenTableColumnVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ExcelProperty(value = "编号")
  private Long columnId;

  /**
   * 归属表编号
   */
  @ExcelProperty(value = "归属表编号")
  private Long tableId;

  /**
   * 列名称
   */
  @ExcelProperty(value = "列名称")
  private String columnName;

  /**
   * 列描述
   */
  @ExcelProperty(value = "列描述")
  private String columnComment;

  /**
   * 列类型
   */
  @ExcelProperty(value = "列类型")
  private String columnType;

  /**
   * JAVA类型
   */
  @ExcelProperty(value = "JAVA类型")
  private String javaType;

  /**
   * JAVA字段名
   */
  @ExcelProperty(value = "JAVA字段名")
  private String javaField;

  /**
   * 是否主键（1是）
   */
  @ExcelProperty(value = "是否主键", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isPk;

  /**
   * 是否自增（1是）
   */
  @ExcelProperty(value = "是否自增", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isIncrement;

  /**
   * 是否必填（1是）
   */
  @ExcelProperty(value = "是否必填", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isRequired;

  /**
   * 是否为插入字段（1是）
   */
  @ExcelProperty(value = "是否为插入字段", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isInsert;

  /**
   * 是否编辑字段（1是）
   */
  @ExcelProperty(value = "是否编辑字段", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isEdit;

  /**
   * 是否列表字段（1是）
   */
  @ExcelProperty(value = "是否列表字段", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isList;

  /**
   * 是否查询字段（1是）
   */
  @ExcelProperty(value = "是否查询字段", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "1=是")
  private String isQuery;

  /**
   * 查询方式（等于、不等于、大于、小于、范围）
   */
  @ExcelProperty(value = "查询方式", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "等=于、不等于、大于、小于、范围")
  private String queryType;

  /**
   * 显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）
   */
  @ExcelProperty(value = "显示类型", converter = ExcelDictConvert.class)
  @ExcelDictFormat(readConverterExp = "文=本框、文本域、下拉框、复选框、单选框、日期控件")
  private String htmlType;

  /**
   * 字典类型
   */
  @ExcelProperty(value = "字典类型")
  private String dictType;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序")
  private Long orderNum;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 更新人
   */
  @ExcelProperty(value = "更新人")
  private String updateByName;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
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

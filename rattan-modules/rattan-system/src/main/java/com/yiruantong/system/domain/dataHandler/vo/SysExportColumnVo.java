package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.dataHandler.SysExportColumn;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 导出字段视图对象 sys_export_column
 *
 * @author YRT
 * @date 2024-05-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysExportColumn.class)
public class SysExportColumnVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 导入字段ID
   */
  @ExcelProperty(value = "导入字段ID")
  private Long columnId;

  /**
   * 导入信息ID
   */
  @ExcelProperty(value = "导入信息ID")
  private Long exportId;

  /**
   * 字段名
   */
  @ExcelProperty(value = "字段名")
  private String columnName;

  /**
   * 字段中文名
   */
  @ExcelProperty(value = "字段中文名")
  private String cnName;

  /**
   * 字段表达式
   */
  @ExcelProperty(value = "字段表达式")
  private String colExpression;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

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
   * 数据类型
   */
  @ExcelProperty(value = "数据类型")
  private String dataType;

  /**
   * 格式化模板
   */
  @ExcelProperty(value = "格式化模板")
  private String dataFormatter;

  /**
   * 开启扩展字段
   */
  @ExcelProperty(value = "开启扩展字段")
  private Byte isExpandField;
}

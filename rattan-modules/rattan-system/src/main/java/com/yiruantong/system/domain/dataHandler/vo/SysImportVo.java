package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysImport;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 导入设置视图对象 sys_import
 *
 * @author YRT
 * @date 2024-07-23
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysImport.class)
public class SysImportVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 导入ID
   */
  @ExcelProperty(value = "导入ID")
  private Long importId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 导入名称
   */
  @ExcelProperty(value = "导入名称")
  private String importName;

  /**
   * 导入类别
   */
  @ExcelProperty(value = "导入类别")
  private Long importType;

  /**
   * 模板路径
   */
  @ExcelProperty(value = "模板路径")
  private String templatePath;

  /**
   * 导入后执行sql
   */
  @ExcelProperty(value = "导入后执行sql")
  private String execSql;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

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
   * 自定义导入ID
   */
  @ExcelProperty(value = "自定义导入ID")
  private Long customImportId;


}

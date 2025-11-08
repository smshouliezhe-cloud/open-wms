package com.yiruantong.system.domain.core.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.core.SysPrintTemplate;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 系统打印模板视图对象 sys_print_template
 *
 * @author YRT
 * @date 2023-11-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysPrintTemplate.class)
public class SysPrintTemplateVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 打印模板ID
   */
  @ExcelProperty(value = "打印模板ID")
  private Long printTemplateId;

  /**
   * 模板名称
   */
  @ExcelProperty(value = "模板名称")
  private String templateName;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private Long menuId;

  /**
   * JSON数据
   */
  @ExcelProperty(value = "JSON数据")
  private String vueData;

  /**
   * 模板类型
   */
  @ExcelProperty(value = "模板类型")
  private String templateType;

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


}

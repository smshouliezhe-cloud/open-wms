package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysExportVueData;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 导出视图设置视图对象 sys_export_vue_data
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysExportVueData.class)
public class SysExportVueDataVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 导出视图ID
   */
  @ExcelProperty(value = "导出视图ID")
  private Long vueDataId;

  /**
   * 导出信息ID
   */
  @ExcelProperty(value = "导出信息ID")
  private Long exportId;

  /**
   * 导出名称
   */
  @ExcelProperty(value = "导出名称")
  private String vueDataName;

  /**
   * 导出JSON
   */
  @ExcelProperty(value = "导出JSON")
  private String vueData;

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
  private Byte enable;

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


}

package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysParamValue;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 下拉框值设置视图对象 sys_param_value
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysParamValue.class)
public class SysParamValueVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long paramId;

  /**
   * 参数类别ID
   */
  @ExcelProperty(value = "参数类别ID")
  private Long typeId;

  /**
   * 参数ID
   */
  @ExcelProperty(value = "参数ID")
  private Long value01;

  /**
   * 参数值1
   */
  @ExcelProperty(value = "参数值1")
  private String value02;

  /**
   * 参数值2
   */
  @ExcelProperty(value = "参数值2")
  private String value03;

  /**
   * 参数值3
   */
  @ExcelProperty(value = "参数值3")
  private String value04;

  /**
   * 参数值4
   */
  @ExcelProperty(value = "参数值4")
  private String value05;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

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
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

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
   * 更新人
   */
  @ExcelProperty(value = "更新人")
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

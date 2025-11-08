package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysParamType;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 下拉框设置视图对象 sys_param_type
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysParamType.class)
public class SysParamTypeVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long typeId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 类别名称
   */
  @ExcelProperty(value = "类别名称")
  private String typeName;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

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
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

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

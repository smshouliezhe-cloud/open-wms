package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysLayout;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 【请填写功能名称】视图对象 sys_layout
 *
 * @author ${author}
 * @date 2024-01-18
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysLayout.class)
public class SysLayoutVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 首页Id
   */
  @ExcelProperty(value = "首页Id")
  private Long layoutId;

  /**
   * jsonData
   */
  @ExcelProperty(value = "jsonData")
  private String jsonData;

  /**
   * 租户编号
   */
  @ExcelProperty(value = "租户编号")
  private String tenantId;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人id
   */
  @ExcelProperty(value = "创建人id")
  private Long createBy;

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
   * 修改人id
   */
  @ExcelProperty(value = "修改人id")
  private Long updateBy;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 删除人ID
   */
  @ExcelProperty(value = "删除人ID")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;


}

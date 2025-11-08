package com.yiruantong.system.domain.task.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.task.TaskWorkflow;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 业务工作流关联设置视图对象 task_workflow
 *
 * @author YRT
 * @date 2024-07-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskWorkflow.class)
public class TaskWorkflowVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 行ID
   */
  @ExcelProperty(value = "行ID")
  private Long rowId;

  /**
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 单据号
   */
  @ExcelProperty(value = "单据号")
  private String billCode;

  /**
   * 审核流部署ID
   */
  @ExcelProperty(value = "审核流部署ID")
  private String deployId;

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
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private Long menuId;

  /**
   * 模块名称
   */
  @ExcelProperty(value = "模块名称")
  private String menuName;


}

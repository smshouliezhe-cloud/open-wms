package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.permission.SysDept;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 部门视图对象 sys_dept
 *
 * @author YiRuanTong
 * @date 2024-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDept.class)
public class SysDeptVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 部门id
   */
  @ExcelProperty(value = "部门id")
  private Long deptId;

  /**
   * 父部门id
   */
  @ExcelProperty(value = "父部门id")
  private Long parentId;

  /**
   * 祖级列表
   */
  @ExcelProperty(value = "祖级列表")
  private String ancestors;

  /**
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 显示顺序
   */
  @ExcelProperty(value = "显示顺序")
  private Long orderNum;

  /**
   * 部门领导ID
   */
  @ExcelProperty(value = "部门领导ID")
  private Long leaderId;

  /**
   * 联系电话
   */
  @ExcelProperty(value = "联系电话")
  private String phone;

  /**
   * 邮箱
   */
  @ExcelProperty(value = "邮箱")
  private String email;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Integer enable;

  /**
   * 删除标志
   */
  @ExcelProperty(value = "删除标志")
  private Integer delFlag;

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
   * 部门领导
   */
  @ExcelProperty(value = "部门领导")
  private String leaderName;

  /**
   * 完全类别路径ID
   */
  @ExcelProperty(value = "完全类别路径ID")
  private String fullDeptId;

  /**
   * 完全类别路径
   */
  @ExcelProperty(value = "完全类别路径")
  private String fullDeptName;


}

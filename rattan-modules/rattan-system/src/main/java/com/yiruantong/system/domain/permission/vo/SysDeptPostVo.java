package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.permission.SysDeptPost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 部门岗位设置视图对象 sys_dept_post
 *
 * @author YRT
 * @date 2024-07-10
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDeptPost.class)
public class SysDeptPostVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 部门岗位ID
   */
  @ExcelProperty(value = "部门岗位ID")
  private Long deptPostId;

  /**
   * 部门id
   */
  @ExcelProperty(value = "部门id")
  private Long deptId;

  /**
   * 部门名称
   */
  @ExcelProperty(value = "部门名称")
  private String deptName;

  /**
   * 岗位ID
   */
  @ExcelProperty(value = "岗位ID")
  private Long postId;

  /**
   * 岗位名称
   */
  @ExcelProperty(value = "岗位名称")
  private String postName;

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
   * 修改人
   */
  @ExcelProperty(value = "修改人")
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


}

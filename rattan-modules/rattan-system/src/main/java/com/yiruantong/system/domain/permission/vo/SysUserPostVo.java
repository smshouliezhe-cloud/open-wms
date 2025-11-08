package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.permission.SysUserPost;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 用户与岗位关联视图对象 sys_user_post
 *
 * @author YRT
 * @date 2024-07-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysUserPost.class)
public class SysUserPostVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 行ID
   */
  @ExcelProperty(value = "行ID")
  private Long userPostId;

  /**
   * 用户ID
   */
  @ExcelProperty(value = "用户ID")
  private Long userId;

  /**
   * 岗位ID
   */
  @ExcelProperty(value = "岗位ID")
  private Long postId;

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

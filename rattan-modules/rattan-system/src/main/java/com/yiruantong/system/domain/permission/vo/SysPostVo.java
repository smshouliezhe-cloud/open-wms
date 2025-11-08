package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.permission.SysPost;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 岗位信息视图对象 sys_post
 *
 * @author YRT
 * @date 2024-07-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysPost.class)
public class SysPostVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 岗位ID
   */
  @ExcelProperty(value = "岗位ID")
  private Long postId;

  /**
   * 岗位编码
   */
  @ExcelProperty(value = "岗位编码")
  private String postCode;

  /**
   * 岗位名称
   */
  @ExcelProperty(value = "岗位名称")
  private String postName;

  /**
   * 显示顺序
   */
  @ExcelProperty(value = "显示顺序")
  private Long postSort;

  /**
   * 状态
   */
  @ExcelProperty(value = "状态")
  private Byte status;

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

  /**
   * 父部门id
   */
  @ExcelProperty(value = "父部门id")
  private Long parentId;

  /**
   * 完整路径ID
   */
  @ExcelProperty(value = "完整路径ID")
  private String fullPostId;

  /**
   * 完整路径
   */
  @ExcelProperty(value = "完整路径")
  private String fullPostName;

  /**
   * 删除标示
   */
  @ExcelProperty(value = "删除标示")
  private Byte delFlag;


}

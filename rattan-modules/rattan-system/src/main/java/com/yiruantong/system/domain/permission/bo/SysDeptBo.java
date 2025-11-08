package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 部门业务对象 sys_dept
 *
 * @author YiRuanTong
 * @date 2024-01-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDept.class, reverseConvertGenerate = false)
public class SysDeptBo extends BaseEntity {

  /**
   * 部门id
   */
  @NotNull(message = "部门id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 父部门id
   */
  @NotNull(message = "父部门id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 祖级列表
   */
  @NotBlank(message = "祖级列表不能为空", groups = {AddGroup.class, EditGroup.class})
  private String ancestors;

  /**
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 显示顺序
   */
  @NotNull(message = "显示顺序不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 部门领导ID
   */
  @NotNull(message = "部门领导ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long leaderId;

  /**
   * 联系电话
   */
  @NotBlank(message = "联系电话不能为空", groups = {AddGroup.class, EditGroup.class})
  private String phone;

  /**
   * 邮箱
   */
  @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, EditGroup.class})
  private String email;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Integer enable;

  /**
   * 删除标志
   */
  private Integer delFlag;

  /**
   * 部门领导
   */
  private String leaderName;

  /**
   * 完全类别路径ID
   */
  @NotBlank(message = "完全类别路径ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullDeptId;

  /**
   * 完全类别路径
   */
  @NotBlank(message = "完全类别路径不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullDeptName;


}

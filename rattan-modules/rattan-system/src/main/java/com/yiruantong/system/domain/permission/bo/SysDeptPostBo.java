package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysDeptPost;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 部门岗位设置业务对象 sys_dept_post
 *
 * @author YRT
 * @date 2024-07-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysDeptPost.class, reverseConvertGenerate = false)
public class SysDeptPostBo extends BaseEntity {

  /**
   * 部门岗位ID
   */
  @NotNull(message = "部门岗位ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptPostId;

  /**
   * 部门id
   */
  @NotNull(message = "部门id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deptId;

  /**
   * 部门名称
   */
  @NotBlank(message = "部门名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deptName;

  /**
   * 岗位ID
   */
  @NotNull(message = "岗位ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long postId;

  /**
   * 岗位名称
   */
  @NotBlank(message = "岗位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String postName;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;


}

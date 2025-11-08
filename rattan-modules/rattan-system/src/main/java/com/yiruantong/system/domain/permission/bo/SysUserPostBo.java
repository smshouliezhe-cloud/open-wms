package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysUserPost;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 用户与岗位关联业务对象 sys_user_post
 *
 * @author YRT
 * @date 2024-07-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysUserPost.class, reverseConvertGenerate = false)
public class SysUserPostBo extends BaseEntity {

  /**
   * 行ID
   */
  @NotNull(message = "行ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userPostId;

  /**
   * 用户ID
   */
  @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 岗位ID
   */
  @NotNull(message = "岗位ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long postId;

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

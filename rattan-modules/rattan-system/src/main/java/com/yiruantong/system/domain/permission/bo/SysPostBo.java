package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysPost;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 岗位信息业务对象 sys_post
 *
 * @author YRT
 * @date 2024-07-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysPost.class, reverseConvertGenerate = false)
public class SysPostBo extends BaseEntity {

  /**
   * 岗位ID
   */
  @NotNull(message = "岗位ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long postId;

  /**
   * 岗位编码
   */
  @NotBlank(message = "岗位编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String postCode;

  /**
   * 岗位名称
   */
  @NotBlank(message = "岗位名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String postName;

  /**
   * 显示顺序
   */
  @NotNull(message = "显示顺序不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long postSort;

  /**
   * 状态
   */
  @NotNull(message = "状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte status;

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

  /**
   * 父部门id
   */
  @NotNull(message = "父部门id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 完整路径ID
   */
  @NotBlank(message = "完整路径ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullPostId;

  /**
   * 完整路径
   */
  @NotBlank(message = "完整路径不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullPostName;

  /**
   * 删除标示
   */
  @NotNull(message = "删除标示不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte delFlag;


}

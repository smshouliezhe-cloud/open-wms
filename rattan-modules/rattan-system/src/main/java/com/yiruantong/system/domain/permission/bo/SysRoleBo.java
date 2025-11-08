package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysRole;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import com.yiruantong.common.core.constant.UserConstants;

/**
 * 角色信息业务对象 sys_role
 *
 * @author YiRuanTong
 * @date 2024-07-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRole.class, reverseConvertGenerate = false)
public class SysRoleBo extends BaseEntity {

  /**
   * 角色ID
   */
  @NotNull(message = "角色ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long roleId;

  /**
   * 角色名称
   */
  @NotBlank(message = "角色名称不能为空", groups = {AddGroup.class, EditGroup.class})
  @Size(min = 0, max = 30, message = "角色名称长度不能超过{max}个字符")
  private String roleName;

  /**
   * 角色权限字符串
   */
  @NotBlank(message = "角色权限字符串不能为空", groups = {AddGroup.class, EditGroup.class})
  private String roleKey;

  /**
   * 显示顺序
   */
  @NotNull(message = "显示顺序不能为空", groups = {AddGroup.class, EditGroup.class})
  private Integer orderNum;

  /**
   * 数据范围
   */
  @NotBlank(message = "数据范围不能为空", groups = {AddGroup.class, EditGroup.class})
  private String dataScope;

  /**
   * 菜单树选择项是否关联显示
   */
  @NotNull(message = "菜单树选择项是否关联显示不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte menuCheckStrictly;

  /**
   * 部门树选择项是否关联显示
   */
  @NotNull(message = "部门树选择项是否关联显示不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte deptCheckStrictly;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

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
   * 菜单组
   */
  private Long[] menuIds;

  /**
   * 部门组（数据权限）
   */
  private Long[] deptIds;

  public SysRoleBo(Long roleId) {
    this.roleId = roleId;
  }

  public boolean isSuperAdmin() {
    return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
  }
}

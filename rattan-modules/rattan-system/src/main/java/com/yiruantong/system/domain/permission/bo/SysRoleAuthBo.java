package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysRoleAuth;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;


/**
 * 功能权限业务对象 sys_role_auth
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRoleAuth.class, reverseConvertGenerate = false)
public class SysRoleAuthBo extends BaseEntity {

  /**
   * 权限ID
   */
  @NotNull(message = "权限ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long authId;

  /**
   * 角色ID
   */
  @NotNull(message = "角色ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long roleId;

  /**
   * 用户ID
   */
  @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 栏目ID
   */
  @NotNull(message = "栏目ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 权限值
   */
  @NotBlank(message = "权限值不能为空", groups = {AddGroup.class, EditGroup.class})
  private String authValue;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;


}

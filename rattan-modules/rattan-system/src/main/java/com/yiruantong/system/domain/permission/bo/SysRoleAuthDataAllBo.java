package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysRoleAuthDataAll;
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
 * 全部数据权限业务对象 sys_role_auth_data_all
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRoleAuthDataAll.class, reverseConvertGenerate = false)
public class SysRoleAuthDataAllBo extends BaseEntity {

  /**
   * 权限ID
   */
  @NotNull(message = "权限ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long authDataAllId;

  /**
   * 模块ID
   */
  @NotNull(message = "模块ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long moduleId;

  /**
   * 角色ID
   */
  @NotNull(message = "角色ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long roleId;

  /**
   * 全部权限
   */
  @NotNull(message = "全部权限不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isAllAuth;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;


}

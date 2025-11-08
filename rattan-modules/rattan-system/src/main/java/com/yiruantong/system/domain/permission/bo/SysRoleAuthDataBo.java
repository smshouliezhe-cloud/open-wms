package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysRoleAuthData;
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
 * 数据权限业务对象 sys_role_auth_data
 *
 * @author YiRuanTong
 * @date 2024-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRoleAuthData.class, reverseConvertGenerate = false)
public class SysRoleAuthDataBo extends BaseEntity {

  /**
   * 权限ID
   */
  @NotNull(message = "权限ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long authDataId;

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
   * 用户ID
   */
  @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long userId;

  /**
   * 模块项ID
   */
  @NotNull(message = "模块项ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long nodeId;

  /**
   * 层次顺序
   */
  @NotNull(message = "层次顺序不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long levelId;

  /**
   * 权限值
   */
  @NotBlank(message = "权限值不能为空", groups = {AddGroup.class, EditGroup.class})
  private String authValue;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 模块项名称
   */
  @NotBlank(message = "模块项名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nodeName;

  /**
   * 节点类别
   */
  @NotBlank(message = "节点类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private String nodeType;


}

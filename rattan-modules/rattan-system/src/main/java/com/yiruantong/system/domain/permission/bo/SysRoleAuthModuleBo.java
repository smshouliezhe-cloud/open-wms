package com.yiruantong.system.domain.permission.bo;

import com.yiruantong.system.domain.permission.SysRoleAuthModule;
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
 * 权限模块业务对象 sys_role_auth_module
 *
 * @author YiRuanTong
 * @date 2024-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysRoleAuthModule.class, reverseConvertGenerate = false)
public class SysRoleAuthModuleBo extends BaseEntity {

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long moduleId;

  /**
   * 模块名称
   */
  @NotBlank(message = "模块名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String moduleName;

  /**
   * 模块SQL
   */
  @NotBlank(message = "模块SQL不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sqlScript;

  /**
   * 是否启用
   */
  @NotNull(message = "是否启用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 编辑类型
   */
  @NotBlank(message = "编辑类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String editType;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * module_type
   */
  @NotBlank(message = "module_type不能为空", groups = {AddGroup.class, EditGroup.class})
  private String moduleType;


}

package com.yiruantong.system.domain.permission;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;

import java.io.Serial;
import java.util.Map;

/**
 * 权限模块对象 sys_role_auth_module
 *
 * @author YiRuanTong
 * @date 2024-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role_auth_module", autoResultMap = true)
public class SysRoleAuthModule extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @TableId(value = "module_id")
  private Long moduleId;

  /**
   * 模块名称
   */
  private String moduleName;

  /**
   * 模块SQL
   */
  private String sqlScript;

  /**
   * 是否启用
   */
  private Long enable;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 备注
   */
  private String remark;

  /**
   * 编辑类型
   */
  private String editType;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * module_type
   */
  private String moduleType;


}

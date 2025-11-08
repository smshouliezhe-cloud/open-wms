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
 * 功能权限对象 sys_role_auth
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role_auth", autoResultMap = true)
public class SysRoleAuth extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 权限ID
   */
  @TableId(value = "auth_id")
  private Long authId;

  /**
   * 角色ID
   */
  private Long roleId;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 栏目ID
   */
  private Long menuId;

  /**
   * 权限值
   */
  private String authValue;

  /**
   * 备注
   */
  private String remark;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;


}

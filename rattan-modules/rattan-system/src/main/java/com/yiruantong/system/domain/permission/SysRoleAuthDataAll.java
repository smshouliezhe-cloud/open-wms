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
 * 全部数据权限对象 sys_role_auth_data_all
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role_auth_data_all", autoResultMap = true)
public class SysRoleAuthDataAll extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 权限ID
   */
  @TableId(value = "auth_data_all_Id")
  private Long authDataAllId;

  /**
   * 模块ID
   */
  private Long moduleId;

  /**
   * 角色ID
   */
  private Long roleId;

  /**
   * 全部权限
   */
  private Long isAllAuth;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;


}

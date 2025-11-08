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
 * 数据权限对象 sys_role_auth_data
 *
 * @author YiRuanTong
 * @date 2024-01-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role_auth_data", autoResultMap = true)
public class SysRoleAuthData extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 权限ID
   */
  @TableId(value = "auth_data_id")
  private Long authDataId;

  /**
   * 模块ID
   */
  private Long moduleId;

  /**
   * 角色ID
   */
  private Long roleId;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 模块项ID
   */
  private Long nodeId;

  /**
   * 层次顺序
   */
  private Long levelId;

  /**
   * 权限值
   */
  private String authValue;

  /**
   * 扩展字段
   */
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 模块项名称
   */
  private String nodeName;

  /**
   * 节点类别
   */
  private String nodeType;


}

package com.yiruantong.system.domain.permission;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 角色信息对象 sys_role
 *
 * @author YiRuanTong
 * @date 2024-07-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_role", autoResultMap = true)
public class SysRole extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 角色ID
   */
  @TableId(value = "role_id")
  private Long roleId;

  /**
   * 角色名称
   */
  private String roleName;

  /**
   * 角色权限字符串
   */
  private String roleKey;

  /**
   * 显示顺序
   */
  private Integer orderNum;

  /**
   * 数据范围
   */
  private String dataScope;

  /**
   * 菜单树选择项是否关联显示
   */
  private Byte menuCheckStrictly;

  /**
   * 部门树选择项是否关联显示
   */
  private Byte deptCheckStrictly;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  public SysRole(Long roleId) {
    this.roleId = roleId;
  }
}

package com.yiruantong.system.domain.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色和部门关联 sys_role_dept
 *
 * @author YiRuanTong
 */

@Data
@TableName(value = "sys_role_dept", autoResultMap = true)
public class SysRoleDept {

  /**
   * 角色ID
   */
  @TableId(type = IdType.INPUT)
  private Long roleId;

  /**
   * 部门ID
   */
  private Long deptId;

}

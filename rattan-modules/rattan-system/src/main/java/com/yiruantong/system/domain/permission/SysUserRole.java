package com.yiruantong.system.domain.permission;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 用户和角色关联对象 sys_user_role
 *
 * @author YRT
 * @date 2024-07-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_role", autoResultMap = true)
public class SysUserRole extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 行ID
   */
  @TableId(value = "user_role_id")
  private Long userRoleId;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 角色ID
   */
  private Long roleId;

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


}

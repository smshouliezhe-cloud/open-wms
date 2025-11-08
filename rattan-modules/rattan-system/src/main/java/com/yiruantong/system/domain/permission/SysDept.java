package com.yiruantong.system.domain.permission;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 部门对象 sys_dept
 *
 * @author YiRuanTong
 * @date 2024-01-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept", autoResultMap = true)
public class SysDept extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 部门id
   */
  @TableId(value = "dept_id")
  private Long deptId;

  /**
   * 父部门id
   */
  private Long parentId;

  /**
   * 祖级列表
   */
  private String ancestors;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 显示顺序
   */
  private Long orderNum;

  /**
   * 部门领导ID
   */
  private Long leaderId;

  /**
   * 联系电话
   */
  private String phone;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 是否可用
   */
  private Integer enable;

  /**
   * 删除标志
   */
  @TableLogic
  private Integer delFlag;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 部门领导
   */
  private String leaderName;

  /**
   * 完全类别路径ID
   */
  private String fullDeptId;

  /**
   * 完全类别路径
   */
  private String fullDeptName;


}

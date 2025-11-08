package com.yiruantong.system.domain.permission;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 部门岗位设置对象 sys_dept_post
 *
 * @author YRT
 * @date 2024-07-10
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_dept_post", autoResultMap = true)
public class SysDeptPost extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 部门岗位ID
   */
  @TableId(value = "dept_post_id")
  private Long deptPostId;

  /**
   * 部门id
   */
  private Long deptId;

  /**
   * 部门名称
   */
  private String deptName;

  /**
   * 岗位ID
   */
  private Long postId;

  /**
   * 岗位名称
   */
  private String postName;

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

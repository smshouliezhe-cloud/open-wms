package com.yiruantong.system.domain.permission;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 用户与岗位关联对象 sys_user_post
 *
 * @author YRT
 * @date 2024-07-08
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_user_post", autoResultMap = true)
public class SysUserPost extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 行ID
   */
  @TableId(value = "user_post_id")
  private Long userPostId;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 岗位ID
   */
  private Long postId;

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

package com.yiruantong.system.domain.permission;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 岗位信息对象 sys_post
 *
 * @author YRT
 * @date 2024-07-26
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_post", autoResultMap = true)
public class SysPost extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 岗位ID
   */
  @TableId(value = "post_id")
  private Long postId;

  /**
   * 岗位编码
   */
  private String postCode;

  /**
   * 岗位名称
   */
  private String postName;

  /**
   * 显示顺序
   */
  private Long postSort;

  /**
   * 状态
   */
  private Byte status;

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

  /**
   * 父部门id
   */
  private Long parentId;

  /**
   * 完整路径ID
   */
  private String fullPostId;

  /**
   * 完整路径
   */
  private String fullPostName;

  /**
   * 删除标示
   */
  @TableLogic
  private Byte delFlag;


}

package com.yiruantong.system.service.permission;

import com.yiruantong.system.domain.permission.SysUserPost;
import com.yiruantong.system.domain.permission.bo.SysUserPostBo;
import com.yiruantong.system.domain.permission.vo.SysUserPostVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 用户与岗位关联Service接口
 *
 * @author YRT
 * @date 2024-07-08
 */
public interface ISysUserPostService extends IServicePlus<SysUserPost, SysUserPostVo, SysUserPostBo> {
  SysUserPost getByUserId(Long userId);

  SysUserPost getByPostId(Long postId);
}

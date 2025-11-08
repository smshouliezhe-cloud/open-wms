package com.yiruantong.system.service.permission.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.permission.SysUserPost;
import com.yiruantong.system.domain.permission.bo.SysUserPostBo;
import com.yiruantong.system.domain.permission.vo.SysUserPostVo;
import com.yiruantong.system.mapper.permission.SysUserPostMapper;
import com.yiruantong.system.service.permission.ISysUserPostService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 用户与岗位关联Service业务层处理
 *
 * @author YRT
 * @date 2024-07-08
 */
@RequiredArgsConstructor
@Service
public class SysUserPostServiceImpl extends ServiceImplPlus<SysUserPostMapper, SysUserPost, SysUserPostVo, SysUserPostBo> implements ISysUserPostService {
  @Override
  public SysUserPost getByUserId(Long userId) {
    LambdaQueryWrapper<SysUserPost> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysUserPost::getUserId, userId);
    return this.getOnly(queryWrapper);
  }

  @Override
  public SysUserPost getByPostId(Long postId) {
    LambdaQueryWrapper<SysUserPost> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysUserPost::getPostId, postId);
    return this.getOnly(queryWrapper);
  }
}

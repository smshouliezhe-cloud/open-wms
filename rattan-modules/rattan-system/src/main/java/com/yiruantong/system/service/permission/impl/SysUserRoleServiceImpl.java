package com.yiruantong.system.service.permission.impl;

import com.yiruantong.system.domain.permission.SysUserRole;
import com.yiruantong.system.domain.permission.bo.SysUserRoleBo;
import com.yiruantong.system.domain.permission.vo.SysUserRoleVo;
import com.yiruantong.system.mapper.permission.SysUserRoleMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.system.service.permission.ISysUserRoleService;

/**
 * 用户和角色关联Service业务层处理
 *
 * @author YRT
 * @date 2024-05-30
 */
@RequiredArgsConstructor
@Service
public class SysUserRoleServiceImpl extends ServiceImplPlus<SysUserRoleMapper, SysUserRole, SysUserRoleVo, SysUserRoleBo> implements ISysUserRoleService {
}

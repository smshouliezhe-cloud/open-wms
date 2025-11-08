package com.yiruantong.system.service.permission.impl;

import com.yiruantong.system.domain.permission.SysRoleAuthModule;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthModuleBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthModuleVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthModuleMapper;
import com.yiruantong.system.service.permission.ISysRoleAuthModuleService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 权限模块Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@RequiredArgsConstructor
@Service
public class SysRoleAuthModuleServiceImpl extends ServiceImplPlus<SysRoleAuthModuleMapper, SysRoleAuthModule, SysRoleAuthModuleVo, SysRoleAuthModuleBo> implements ISysRoleAuthModuleService {
}

package com.yiruantong.system.service.permission.impl;

import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthDataBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthDataVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthDataMapper;
import com.yiruantong.system.service.permission.ISysRoleAuthDataService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 数据权限Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@RequiredArgsConstructor
@Service
public class SysRoleAuthDataServiceImpl extends ServiceImplPlus<SysRoleAuthDataMapper, SysRoleAuthData, SysRoleAuthDataVo, SysRoleAuthDataBo> implements ISysRoleAuthDataService {
}

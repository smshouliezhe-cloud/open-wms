package com.yiruantong.system.service.permission.impl;

import com.yiruantong.system.domain.permission.SysRoleAuthDataAll;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthDataAllBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthDataAllVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthDataAllMapper;
import com.yiruantong.system.service.permission.ISysRoleAuthDataAllService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

/**
 * 全部数据权限Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@RequiredArgsConstructor
@Service
public class SysRoleAuthDataAllServiceImpl extends ServiceImplPlus<SysRoleAuthDataAllMapper, SysRoleAuthDataAll, SysRoleAuthDataAllVo, SysRoleAuthDataAllBo> implements ISysRoleAuthDataAllService {
}

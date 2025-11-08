package com.yiruantong.system.service.permission;

import com.yiruantong.system.domain.permission.SysRoleAuthModule;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthModuleBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthModuleVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 权限模块Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
public interface ISysRoleAuthModuleService extends IServicePlus<SysRoleAuthModule, SysRoleAuthModuleVo, SysRoleAuthModuleBo> {
}

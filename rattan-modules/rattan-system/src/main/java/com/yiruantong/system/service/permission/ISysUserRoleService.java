package com.yiruantong.system.service.permission;

import com.yiruantong.system.domain.permission.SysUserRole;
import com.yiruantong.system.domain.permission.bo.SysUserRoleBo;
import com.yiruantong.system.domain.permission.vo.SysUserRoleVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 用户和角色关联Service接口
 *
 * @author YRT
 * @date 2024-05-30
 */
public interface ISysUserRoleService extends IServicePlus<SysUserRole, SysUserRoleVo, SysUserRoleBo> {
}

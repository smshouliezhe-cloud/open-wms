package com.yiruantong.system.mapper.permission;

import com.yiruantong.system.domain.permission.SysUserRole;
import com.yiruantong.system.domain.permission.vo.SysUserRoleVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 用户与角色关联表 数据层
 *
 * @author YiRuanTong
 */
public interface SysUserRoleMapper extends BaseMapperPlus<SysUserRole, SysUserRoleVo> {

  List<Long> selectUserIdsByRoleId(Long roleId);

}

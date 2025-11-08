package com.yiruantong.system.service.permission;

import com.yiruantong.system.domain.permission.SysRoleAuthData;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthDataBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthDataVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 数据权限Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
public interface ISysRoleAuthDataService extends IServicePlus<SysRoleAuthData, SysRoleAuthDataVo, SysRoleAuthDataBo> {
}

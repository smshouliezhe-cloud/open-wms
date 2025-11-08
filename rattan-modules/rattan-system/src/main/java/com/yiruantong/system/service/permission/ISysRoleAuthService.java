package com.yiruantong.system.service.permission;

import com.yiruantong.system.domain.permission.SysRoleAuth;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 功能权限Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
public interface ISysRoleAuthService extends IServicePlus<SysRoleAuth, SysRoleAuthVo, SysRoleAuthBo> {
  /**
   * 保存菜单权限
   *
   * @param map 保存数据
   * @return 返回R
   */
  R<Void> saveAuthMenu(Map<String, Object> map);

  /**
   * 获取用户数据权限
   *
   * @param map 保存数据
   * @return 返回R
   */
  R<List<Map<String, Object>>> getDataAuth(Map<String, Object> map);

  /**
   * 保存用户数据权限
   */
  R<List<Map<String, Object>>> saveUserDataAuth(Map<String, Object> map);
}

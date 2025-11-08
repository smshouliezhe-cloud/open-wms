package com.yiruantong.system.service.permission;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.ModifyPhoneNumberBody;

import java.util.Set;

/**
 * 用户权限处理
 *
 * @author YiRuanTong
 */
public interface ISysPermissionService {

  /**
   * 获取角色数据权限
   *
   * @param userId 用户id
   * @return 角色权限信息
   */
  Set<String> getRolePermission(Long userId);

  /**
   * 获取菜单数据权限
   *
   * @param userId 用户id
   * @return 菜单权限信息
   */
  Set<String> getMenuPermission(Long userId);

  /**
   * 发送激活邮件
   *
   * @return R
   */
  R<Void> sendActiveEmail();

  /**
   * 激活邮件
   *
   * @return R
   */
  String emailActivate(String key);

  R<Void> sendPhoneActivate(ModifyPhoneNumberBody phoneNumberBody);
}

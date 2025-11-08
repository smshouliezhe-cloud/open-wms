package com.yiruantong.system.service.tenant;

import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.bo.SysTenantProfileBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantProfileVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

/**
 * 租户档案Service接口
 *
 * @author YRT
 * @date 2024-05-14
 */
public interface ISysTenantProfileService extends IServicePlus<SysTenantProfile, SysTenantProfileVo, SysTenantProfileBo> {
  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  boolean checkPhoneUnique(SysTenantProfileBo user);

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  boolean checkEmailUnique(SysTenantProfileBo user);

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  boolean registerUser(SysTenantProfileBo user);

  /**
   * 获取profile用户信息
   *
   * @param mobile 用户信息
   * @return profile结果
   */
  SysTenantProfile getProfile(String mobile);

  /**
   * 修改手机号
   *
   * @param newPhoneNumber 新手机号
   * @return 结果
   */
  boolean modifyPhoneNumber(String newPhoneNumber);

  /**
   * 修改email
   *
   * @param email 新email
   * @return 结果
   */
  boolean modifyEmail(String email);

  /**
   * 激活email
   *
   * @param email 新email
   * @return 结果
   */
  boolean emailActivate(String email);

  /**
   * 激活手机号
   *
   * @param phoneNumber 手机号
   * @return boolean
   */
  boolean phoneActivate(String phoneNumber);
}

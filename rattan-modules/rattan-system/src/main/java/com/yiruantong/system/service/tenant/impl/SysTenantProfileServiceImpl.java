package com.yiruantong.system.service.tenant.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.bo.SysTenantProfileBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantProfileVo;
import com.yiruantong.system.mapper.tenant.SysTenantProfileMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.system.service.tenant.ISysTenantProfileService;
import org.springframework.stereotype.Service;

/**
 * 租户档案Service业务层处理
 *
 * @author YRT
 * @date 2024-05-14
 */
@RequiredArgsConstructor
@Service
public class SysTenantProfileServiceImpl extends ServiceImplPlus<SysTenantProfileMapper, SysTenantProfile, SysTenantProfileVo, SysTenantProfileBo> implements ISysTenantProfileService {

  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   */
  @Override
  public boolean checkPhoneUnique(SysTenantProfileBo user) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysTenantProfile>()
      .eq(SysTenantProfile::getPhoneNumber, user.getPhoneNumber())
      .ne(ObjectUtil.isNotNull(user.getProfileId()), SysTenantProfile::getProfileId, user.getProfileId()));
    return !exist;
  }

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   */
  @Override
  public boolean checkEmailUnique(SysTenantProfileBo user) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysTenantProfile>()
      .eq(SysTenantProfile::getEmail, user.getEmail())
      .ne(ObjectUtil.isNotNull(user.getProfileId()), SysTenantProfile::getProfileId, user.getProfileId()));
    return !exist;
  }

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean registerUser(SysTenantProfileBo user) {
    SysTenantProfile sysUser = MapstructUtils.convert(user, SysTenantProfile.class);
    return baseMapper.insert(sysUser) > 0;
  }

  @Override
  public SysTenantProfile getProfile(String mobile) {
    LambdaQueryWrapper<SysTenantProfile> profileLambdaQueryWrapper = new LambdaQueryWrapper<>();
    profileLambdaQueryWrapper.eq(SysTenantProfile::getPhoneNumber, mobile);

    return this.getOne(profileLambdaQueryWrapper);
  }

  @Override
  public boolean modifyPhoneNumber(String newPhoneNumber) {
    LambdaUpdateWrapper<SysTenantProfile> profileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    profileLambdaUpdateWrapper.set(SysTenantProfile::getPhoneNumber, newPhoneNumber)
      .eq(SysTenantProfile::getPhoneNumber, LoginHelper.getPhoneNumber());
    return this.update(profileLambdaUpdateWrapper);
  }

  @Override
  public boolean modifyEmail(String email) {
    LambdaUpdateWrapper<SysTenantProfile> profileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    profileLambdaUpdateWrapper.set(SysTenantProfile::getEmail, email)
      .eq(SysTenantProfile::getPhoneNumber, LoginHelper.getPhoneNumber());
    return this.update(profileLambdaUpdateWrapper);
  }

  @Override
  public boolean emailActivate(String email) {
    LambdaUpdateWrapper<SysTenantProfile> profileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    profileLambdaUpdateWrapper.set(SysTenantProfile::getEmailVerified, EnableEnum.ENABLE.getId())
      .eq(SysTenantProfile::getEmail, email);
    return this.update(profileLambdaUpdateWrapper);
  }

  @Override
  public boolean phoneActivate(String phoneNumber) {
    LambdaUpdateWrapper<SysTenantProfile> profileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    profileLambdaUpdateWrapper.set(SysTenantProfile::getPhoneVerified, EnableEnum.ENABLE.getId())
      .eq(SysTenantProfile::getPhoneNumber, phoneNumber);
    return this.update(profileLambdaUpdateWrapper);
  }
}

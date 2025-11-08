package com.yiruantong.web.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.domain.model.LoginBody;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.user.LoginTypeEnum;
import com.yiruantong.common.core.enums.user.UserStatusEnum;
import com.yiruantong.common.core.exception.user.CaptchaExpireException;
import com.yiruantong.common.core.exception.user.UserException;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.ValidatorUtils;
import com.yiruantong.common.core.validate.auth.SmsGroup;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.system.mapper.permission.SysUserMapper;
import com.yiruantong.web.domain.vo.LoginVo;
import com.yiruantong.web.service.IAuthStrategy;
import com.yiruantong.web.service.SysLoginService;
import org.springframework.stereotype.Service;

/**
 * 短信认证策略
 *
 * @author YiRuanTong
 */
@Slf4j
@Service("sms" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class SmsAuthStrategy implements IAuthStrategy {

  private final SysLoginService loginService;
  private final SysUserMapper userMapper;

  @Override
  public void validate(LoginBody loginBody) {
    ValidatorUtils.validate(loginBody, SmsGroup.class);
  }

  @Override
  public LoginVo login(String clientId, LoginBody loginBody, SysClient client) {
    String tenantId = loginBody.getTenantId();
    String phoneNumber = loginBody.getPhoneNumber();
    String smsCode = loginBody.getSmsCode();

    // 通过手机号查找用户
    SysUserVo user = loadUserByPhonenumber(tenantId, phoneNumber);

    loginService.checkLogin(LoginTypeEnum.SMS, tenantId, user.getUserName(), () -> !validateSmsCode(tenantId, phoneNumber, smsCode));
    // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
    LoginUser loginUser = loginService.buildLoginUser(tenantId, user);
    SaLoginModel model = new SaLoginModel();
    model.setDevice(client.getDeviceType());
    // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
    // 例如: 后台用户30分钟过期 app用户1天过期
    model.setTimeout(client.getTimeout());
    model.setActiveTimeout(client.getActiveTimeout());
    model.setExtra(LoginHelper.CLIENT_ID, clientId);
    model.setExtra(LoginHelper.CLIENT_KEY, client.getClientKey());
    // 生成token
    LoginHelper.login(loginUser, model);

    loginService.recordLogininfor(loginUser.getTenantId(), user.getUserName(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
    loginService.recordLoginInfo(user.getUserId());

    LoginVo loginVo = new LoginVo();
    loginVo.setAccessToken(StpUtil.getTokenValue());
    loginVo.setExpireIn(StpUtil.getTokenTimeout());
    loginVo.setClientId(clientId);
    return loginVo;
  }

  /**
   * 校验短信验证码
   */
  private boolean validateSmsCode(String tenantId, String phoneNumber, String smsCode) {
    String key = GlobalConstants.CAPTCHA_CODE_KEY + phoneNumber;
    String code = RedisUtils.getCacheObject(key);
    if (StringUtils.isBlank(code)) {
      loginService.recordLogininfor(tenantId, phoneNumber, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
      throw new CaptchaExpireException();
    }
    RedisUtils.deleteObject(key);
    return code.equals(smsCode);
  }

  private SysUserVo loadUserByPhonenumber(String tenantId, String phoneNumber) {
    SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
      .select(SysUser::getPhoneNumber, SysUser::getEnable)
      .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId)
      .eq(SysUser::getPhoneNumber, phoneNumber));
    if (ObjectUtil.isNull(user)) {
      log.info("登录用户：{} 不存在.", phoneNumber);
      throw new UserException("user.not.exists", phoneNumber);
    } else if (UserStatusEnum.DISABLE.getCode().equals(user.getEnable())) {
      log.info("登录用户：{} 已被停用.", phoneNumber);
      throw new UserException("user.blocked", phoneNumber);
    }
    if (TenantHelper.isEnable()) {
      return userMapper.selectTenantUserByPhoneNumber(phoneNumber, tenantId);
    }
    return userMapper.selectUserByPhoneNumber(phoneNumber);
  }

}

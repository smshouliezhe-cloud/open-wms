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
import com.yiruantong.common.core.validate.auth.EmailGroup;
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
 * 邮件认证策略
 *
 * @author YiRuanTong
 */
@Slf4j
@Service("email" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class EmailAuthStrategy implements IAuthStrategy {

  private final SysLoginService loginService;
  private final SysUserMapper userMapper;

  @Override
  public void validate(LoginBody loginBody) {
    ValidatorUtils.validate(loginBody, EmailGroup.class);
  }

  @Override
  public LoginVo login(String clientId, LoginBody loginBody, SysClient client) {
    String tenantId = loginBody.getTenantId();
    String email = loginBody.getEmail();
    String emailCode = loginBody.getEmailCode();

    // 通过邮箱查找用户
    SysUserVo user = loadUserByEmail(tenantId, email);

    loginService.checkLogin(LoginTypeEnum.EMAIL, tenantId, user.getUserName(), () -> !validateEmailCode(tenantId, email, emailCode));
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
   * 校验邮箱验证码
   */
  private boolean validateEmailCode(String tenantId, String email, String emailCode) {
    String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + email);
    if (StringUtils.isBlank(code)) {
      loginService.recordLogininfor(tenantId, email, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
      throw new CaptchaExpireException();
    }
    return code.equals(emailCode);
  }

  private SysUserVo loadUserByEmail(String tenantId, String email) {
    SysUser user = userMapper.selectOne(new LambdaQueryWrapper<SysUser>()
      .select(SysUser::getEmail, SysUser::getEnable)
      .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId)
      .eq(SysUser::getEmail, email));
    if (ObjectUtil.isNull(user)) {
      log.info("登录用户：{} 不存在.", email);
      throw new UserException("user.not.exists", email);
    } else if (UserStatusEnum.DISABLE.getCode().equals(user.getEnable())) {
      log.info("登录用户：{} 已被停用.", email);
      throw new UserException("user.blocked", email);
    }
    if (TenantHelper.isEnable()) {
      return userMapper.selectTenantUserByEmail(email, tenantId);
    }
    return userMapper.selectUserByEmail(email);
  }

}

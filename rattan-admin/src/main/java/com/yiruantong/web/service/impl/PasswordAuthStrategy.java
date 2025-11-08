package com.yiruantong.web.service.impl;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.domain.model.LoginBody;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.tenant.ClientKeyEnum;
import com.yiruantong.common.core.enums.user.LoginTypeEnum;
import com.yiruantong.common.core.enums.user.UserStatusEnum;
import com.yiruantong.common.core.exception.user.CaptchaException;
import com.yiruantong.common.core.exception.user.CaptchaExpireException;
import com.yiruantong.common.core.exception.user.UserException;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.ServletUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.ValidatorUtils;
import com.yiruantong.common.core.validate.auth.PasswordGroup;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.exception.TenantException;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.common.web.config.properties.CaptchaProperties;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.mapper.permission.SysUserMapper;
import com.yiruantong.system.service.tenant.ISysClientService;
import com.yiruantong.system.service.tenant.ISysTenantProfileService;
import com.yiruantong.system.service.tenant.ISysTenantService;
import com.yiruantong.web.domain.vo.LoginVo;
import com.yiruantong.web.service.IAuthStrategy;
import com.yiruantong.web.service.SysLoginService;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 密码认证策略
 *
 * @author YiRuanTong
 */
@Slf4j
@Service("password" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class PasswordAuthStrategy implements IAuthStrategy {

  private final CaptchaProperties captchaProperties;
  private final SysLoginService loginService;
  private final SysUserMapper userMapper;
  private final ISysTenantProfileService sysTenantProfileService;
  private final ISysTenantService sysTenantService;
  private final ISysClientService sysClientService;

  @Override
  public void validate(LoginBody loginBody) {
    ValidatorUtils.validate(loginBody, PasswordGroup.class);
  }

  @Override
  public LoginVo login(String clientId, LoginBody loginBody, SysClient client) {
    String tenantId = loginBody.getTenantId();
    String username = loginBody.getUsername();
    String password = loginBody.getPassword();
    String code = loginBody.getCode();
    String uuid = loginBody.getUuid();

    // 密码输入错误次数，更新需要验证码次数
    String uniqueIdKey = GlobalConstants.CAPTCHA_CODE_KEY + ServletUtils.getUniqueId();
    Integer checkNum = RedisUtils.getCacheObject(uniqueIdKey);
    if (ObjectUtil.isNull(checkNum)) {
      checkNum = 0;
    }

    RedisUtils.setCacheObject(uniqueIdKey, ++checkNum);

    boolean captchaEnabled = captchaProperties.getEnable();
    // 验证码开关，非API段需要验证验证码
    if (captchaEnabled && checkNum > 2 && !StringUtils.equals(client.getClientKey(), ClientKeyEnum.API.getName())) {
      validateCaptcha(tenantId, username, code, uuid);
    }

    SysUserVo user = loadUserByUsername(tenantId, username);
    tenantId = user.getTenantId();
    // 此处可根据登录用户的数据不同 自行创建 loginUser
    LoginUser loginUser = loginService.buildLoginUser(tenantId, user);

    // 如果该登录用户 ”是否可用“ 关闭时，不让登录
    Long enable = Long.valueOf(user.getEnable());
    if (ObjectUtil.equal(enable, 0L)) {
      throw new TenantException("当前账号尚未通过审核，请联系管理员！");
    }
    if (!ObjectUtil.equal(enable, 1L)) {
      throw new TenantException("该账号已停用，请联系系统管理员！");
    }

    loginService.checkLogin(LoginTypeEnum.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));

    SaLoginModel model = new SaLoginModel();
    model.setDevice(client.getDeviceType());
    // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
    // 例如: 后台用户30分钟过期 app用户1天过期
    model.setTimeout(client.getTimeout());
    model.setActiveTimeout(client.getActiveTimeout());
    model.setExtra(LoginHelper.CLIENT_ID, clientId);
    model.setExtra(LoginHelper.CLIENT_KEY, client.getClientKey());
    model.setExtra(LoginHelper.CLIENT_SECRET, client.getClientSecret());
    // 生成token
    LoginHelper.login(loginUser, model);

    // 授权密码不能小于16位
    Assert.isFalse(StrUtil.length(client.getClientSecret()) < 16, MessageUtils.message("sys.client.password.not.length"));
    // 自定义API鉴权密码
    SaSignConfig saSignConfig = new SaSignConfig();
    saSignConfig.setSecretKey(client.getClientSecret());
    SaManager.getSaSignTemplate().setSignConfig(saSignConfig);

    loginService.recordLogininfor(tenantId, username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
    loginService.recordLoginInfo(user.getUserId());

    LoginVo loginVo = new LoginVo();
    loginVo.setAccessToken(StpUtil.getTokenValue());
    loginVo.setExpireIn(StpUtil.getTokenTimeout());
    loginVo.setClientId(clientId);
    loginVo.setScope("real_login");

    RedisUtils.setCacheObject(uniqueIdKey, 0); // 验证码次数重置为0

    if (ObjectUtil.isNotEmpty(loginBody.getRedirect())) {
      String redirect = SaFoxUtil.decoderUrl(loginBody.getRedirect());
      if (loginBody.getRedirect().indexOf("http") == 0) {
        // 构建单点登录返回ticket，已登录情况下，构建 redirectUrl
        String mode = "ticket";
        String _client = "";
        // 模式二或模式三
        String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), _client, redirect);
        loginVo.setRedirectUrl(redirectUrl);
      } else {
        loginVo.setRedirectUrl(redirect);
      }
    }

    return loginVo;
  }

  @Override
  public LoginVo loginProfile(String clientId, LoginBody loginBody, SysClient client) {
    String tenantId = loginBody.getTenantId();
    String username = loginBody.getUsername();
    String password = loginBody.getPassword();
    String code = loginBody.getCode();
    String uuid = loginBody.getUuid();

    // 密码输入错误次数，更新需要验证码次数
    String uniqueIdKey = GlobalConstants.CAPTCHA_CODE_KEY + ServletUtils.getUniqueId();
    Integer checkNum = RedisUtils.getCacheObject(uniqueIdKey);
    if (ObjectUtil.isNull(checkNum)) {
      checkNum = 0;
    }
    RedisUtils.setCacheObject(uniqueIdKey, ++checkNum);

    boolean captchaEnabled = captchaProperties.getEnable();
    // 验证码开关
    if (captchaEnabled && checkNum > 2) {
      validateCaptcha(tenantId, username, code, uuid);
    }

    // 是否存在多账套
    AtomicReference<String> refTenantId = new AtomicReference<>();
    boolean multiTenant = sysTenantService.isExistMultiTenant(username, refTenantId, password);
    tenantId = refTenantId.get();
    loginBody.setTenantId(tenantId);
    if (multiTenant) {
      //#region 预登陆，进入账套选择
      SysTenantProfile user = getTenantProfile(username);
      // 此处可根据登录用户的数据不同 自行创建 loginUser
      LoginUser loginUser = loginService.buildLoginProfile(tenantId, user);

      // 如果该登录用户 ”是否可用“ 关闭时，不让登录
      Long enable = Long.valueOf(user.getEnable());
      if (!ObjectUtil.equal(enable, 1L)) {
        throw new TenantException("该账号已停用，请联系系统管理员！");
      }
      loginService.checkLogin(LoginTypeEnum.PASSWORD, tenantId, username, () -> !BCrypt.checkpw(password, user.getPassword()));

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

      loginService.recordLogininfor(loginUser.getTenantId(), username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
//    loginService.recordLoginInfo(user.getUserId());

      LoginVo loginVo = new LoginVo();
      loginVo.setAccessToken(StpUtil.getTokenValue());
      loginVo.setExpireIn(StpUtil.getTokenTimeout());
      loginVo.setClientId(clientId);

      RedisUtils.setCacheObject(uniqueIdKey, 0); // 验证码次数重置为0
      return loginVo;
      //#endregion
    } else {
      //#region 常规登录，单账套直接进行登录
      RedisUtils.setCacheObject(uniqueIdKey, 0); // 验证码次数重置为0
      return login(clientId, loginBody, client);
      //#endregion
    }
  }

  @Override
  public LoginVo loginSso(String clientId, LoginBody loginBody, SysClient client) {
    String tenantId = loginBody.getTenantId();
    String username = loginBody.getUsername();
    String password = loginBody.getPassword();
    String code = loginBody.getCode();
    String uuid = loginBody.getUuid();

    // 密码输入错误次数，更新需要验证码次数
//    String uniqueIdKey = GlobalConstants.CAPTCHA_CODE_KEY + ServletUtils.getUniqueId();
//    Integer checkNum = RedisUtils.getCacheObject(uniqueIdKey);
//    if (ObjectUtil.isNull(checkNum)) {
//      checkNum = 0;
//    }
//
//    RedisUtils.setCacheObject(uniqueIdKey, ++checkNum);

//    boolean captchaEnabled = captchaProperties.getEnable();
//    // 验证码开关，非API段需要验证验证码
//    if (captchaEnabled && checkNum > 2 && !StringUtils.equals(client.getClientKey(), ClientKeyEnum.API.getName())) {
//      validateCaptcha(tenantId, username, code, uuid);
//    }

    SysUserVo user = loadUserByUsernameTel(tenantId, username, loginBody.getPhoneNumber());
    // 此处可根据登录用户的数据不同 自行创建 loginUser
    LoginUser loginUser = loginService.buildLoginUser(tenantId, user);

    // 如果该登录用户 ”是否可用“ 关闭时，不让登录
    Long enable = Long.valueOf(user.getEnable());
    if (ObjectUtil.equal(enable, 0L)) {
      throw new TenantException("当前账号尚未通过审核，请联系管理员！");
    }
    if (!ObjectUtil.equal(enable, 1L)) {
      throw new TenantException("该账号已停用，请联系系统管理员！");
    }

    SaLoginModel model = new SaLoginModel();
    model.setDevice(client.getDeviceType());
    // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
    // 例如: 后台用户30分钟过期 app用户1天过期
    model.setTimeout(client.getTimeout());
    model.setActiveTimeout(client.getActiveTimeout());
    model.setExtra(LoginHelper.CLIENT_ID, clientId);
    model.setExtra(LoginHelper.CLIENT_KEY, client.getClientKey());
    model.setExtra(LoginHelper.CLIENT_SECRET, client.getClientSecret());
    // 生成token
    LoginHelper.login(loginUser, model);

    // 授权密码不能小于16位
    Assert.isFalse(StrUtil.length(client.getClientSecret()) < 16, MessageUtils.message("sys.client.password.not.length"));
    // 自定义API鉴权密码
    SaSignConfig saSignConfig = new SaSignConfig();
    saSignConfig.setSecretKey(client.getClientSecret());
    SaManager.getSaSignTemplate().setSignConfig(saSignConfig);

    loginService.recordLogininfor(loginUser.getTenantId(), username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success"));
    loginService.recordLoginInfo(user.getUserId());

    LoginVo loginVo = new LoginVo();
    loginVo.setAccessToken(StpUtil.getTokenValue());
    loginVo.setExpireIn(StpUtil.getTokenTimeout());
    loginVo.setClientId(clientId);
    loginVo.setScope("real_login");

//    RedisUtils.setCacheObject(uniqueIdKey, 0); // 验证码次数重置为0

    if (ObjectUtil.isNotEmpty(loginBody.getRedirect())) {
      String redirect = SaFoxUtil.decoderUrl(loginBody.getRedirect());
      if (loginBody.getRedirect().indexOf("http") == 0) {
        // 构建单点登录返回ticket，已登录情况下，构建 redirectUrl
        String mode = "ticket";
        String _client = "";
        // 模式二或模式三
        String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), _client, redirect);
        loginVo.setRedirectUrl(redirectUrl);
      } else {
        loginVo.setRedirectUrl(redirect);
      }
    }

    return loginVo;
  }

  /**
   * 校验验证码
   *
   * @param username 用户名
   * @param code     验证码
   * @param uuid     唯一标识
   */
  private void validateCaptcha(String tenantId, String username, String code, String uuid) {
    String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + StringUtils.defaultString(uuid, "");
    String captcha = RedisUtils.getCacheObject(verifyKey);
    RedisUtils.deleteObject(verifyKey);
    if (captcha == null || code == null) {
      loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"));
      throw new CaptchaExpireException();
    }
    if (!code.equalsIgnoreCase(captcha)) {
      loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"));
      throw new CaptchaException();
    }
  }

  private SysUserVo loadUserByUsername(String tenantId, String username) {
    LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<SysUser>()
      .select(SysUser::getUserName, SysUser::getEnable)
      .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId)
      .and((sub) -> {
        sub.eq(SysUser::getUserName, username)
          .or()
          .eq(SysUser::getPhoneNumber, username)
          .or()
          .eq(SysUser::getEmail, username);
      });
    SysUser user = userMapper.selectOne(sysUserLambdaQueryWrapper);
    if (ObjectUtil.isNull(user)) {
      log.info("登录用户：{} 不存在.", username);
      throw new UserException("user.not.exists", username);
    } else if (UserStatusEnum.DISABLE.getCode().equals(user.getEnable())) {
      log.info("登录用户：{} 已被停用.", username);
      throw new UserException("user.blocked", username);
    }
    if (TenantHelper.isEnable()) {
      return userMapper.selectTenantUserByUserName(username, tenantId);
    }
    return userMapper.selectUserByUserName(username);
  }

  private SysUserVo loadUserByUsernameTel(String tenantId, String username, String phoneNumber) {
    LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<SysUser>()
      .select(SysUser::getUserName, SysUser::getEnable)
      .eq(TenantHelper.isEnable(), SysUser::getTenantId, tenantId)
      .eq(SysUser::getUserName, username)
      .eq(SysUser::getPhoneNumber, phoneNumber);
    SysUser user = userMapper.selectOne(sysUserLambdaQueryWrapper);
    if (ObjectUtil.isNull(user)) {
      log.info("登录用户：{} 不存在.", username);
      throw new UserException("user.not.exists", username);
    } else if (UserStatusEnum.DISABLE.getCode().equals(user.getEnable())) {
      log.info("登录用户：{} 已被停用.", username);
      throw new UserException("user.blocked", username);
    }
    if (TenantHelper.isEnable()) {
      return userMapper.selectTenantUserByUserName(username, tenantId);
    }
    return userMapper.selectUserByUserName(username);
  }

  private SysTenantProfile getTenantProfile(String username) {
    LambdaQueryWrapper<SysTenantProfile> profileLambdaQueryWrapper = new LambdaQueryWrapper<SysTenantProfile>()
      .eq(SysTenantProfile::getEnable, EnableEnum.ENABLE.getId())
      .and((sub) -> {
        sub.eq(SysTenantProfile::getUserName, username)
          .or()
          .eq(SysTenantProfile::getPhoneNumber, username)
          .or()
          .eq(SysTenantProfile::getEmail, username);
      });
    SysTenantProfile user = sysTenantProfileService.getOne(profileLambdaQueryWrapper);

    if (ObjectUtil.isNull(user)) {
      log.info("登录用户：{} 不存在.", username);
      throw new UserException("profile.not.exists", username);
    } else if (UserStatusEnum.DISABLE.getCode().equals(user.getEnable())) {
      log.info("登录用户：{} 已被停用.", username);
      throw new UserException("profile.blocked", username);
    }

    return user;
  }

}

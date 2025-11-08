package com.yiruantong.web.service.impl;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.basic.domain.tms.BaseDriver;
import com.yiruantong.basic.service.tms.IBaseDriverService;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.domain.model.LoginBody;
import com.yiruantong.common.core.domain.model.LoginUser;
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
import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.web.domain.vo.LoginVo;
import com.yiruantong.web.service.IAuthStrategy;
import com.yiruantong.web.service.SysLoginService;
import org.springframework.stereotype.Service;

/**
 * 司机密码认证策略
 *
 * @author xtb
 */
@Slf4j
@Service("driver" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class DriverAuthStrategy implements IAuthStrategy {

  private final CaptchaProperties captchaProperties;
  private final SysLoginService loginService;
  private final IBaseDriverService baseDriverService;

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
    // 验证码开关
    if (captchaEnabled && checkNum > 2) {
      validateCaptcha(tenantId, username, code, uuid);
    }

    BaseDriver user = loadUserByUsername(tenantId, username);
    // 此处可根据登录用户的数据不同 自行创建 loginUser
    LoginUser loginUser = loginService.buildLoginUser(tenantId, user);

    // 如果该登录用户 ”是否可用“ 关闭时，不让登录
    Long enable = Long.valueOf(user.getEnable());
    if (!ObjectUtil.equal(enable, 1L)) {
      throw new TenantException("该账号已停用，请联系系统管理员！");
    }
    loginService.checkLogin(LoginTypeEnum.DRIVER, tenantId, username, () -> !BCrypt.checkpw(password, user.getUserPwd()));

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

    // 授权密码不能小于16位
    Assert.isFalse(StrUtil.length(client.getClientSecret()) < 16, MessageUtils.message("sys.client.password.not.length"));
    // 自定义API鉴权密码
    SaSignConfig saSignConfig = new SaSignConfig();
    saSignConfig.setSecretKey(client.getClientKey());
    SaManager.getSaSignTemplate().setSignConfig(saSignConfig);

    loginService.recordLogininfor(loginUser.getTenantId(), username, Constants.LOGIN_SUCCESS, MessageUtils.message("driver.login.success"));
    loginService.recordLoginInfoConsignor(user.getDriverId());

    LoginVo loginVo = new LoginVo();
    loginVo.setAccessToken(StpUtil.getTokenValue());
    loginVo.setExpireIn(StpUtil.getTokenTimeout());
    loginVo.setClientId(clientId);
    loginVo.setScope("real_login");

    RedisUtils.setCacheObject(uniqueIdKey, 0); // 验证码次数重置为0

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
      loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("driver.jcaptcha.expire"));
      throw new CaptchaExpireException();
    }
    if (!code.equalsIgnoreCase(captcha)) {
      loginService.recordLogininfor(tenantId, username, Constants.LOGIN_FAIL, MessageUtils.message("driver.jcaptcha.error"));
      throw new CaptchaException();
    }
  }

  private BaseDriver loadUserByUsername(String tenantId, String username) {
    LambdaQueryWrapper<BaseDriver> consignorLambdaQueryWrapper = new LambdaQueryWrapper<BaseDriver>();
    consignorLambdaQueryWrapper
      .eq(TenantHelper.isEnable(), BaseDriver::getTenantId, tenantId)
      .and(a -> a.eq(BaseDriver::getUserName, username)
        .or()
        .eq(BaseDriver::getTel, username)
      );

    BaseDriver user = baseDriverService.getOne(consignorLambdaQueryWrapper);
    if (ObjectUtil.isNull(user)) {
      log.info("登录司机：{} 不存在.", username);
      throw new UserException("driver.not.exists", username);
    } else if (UserStatusEnum.DISABLE.getCode().equals(user.getEnable())) {
      log.info("登录司机：{} 已被停用.", username);
      throw new UserException("driver.blocked", username);
    }
    return user;
  }

}

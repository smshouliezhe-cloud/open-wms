package com.yiruantong.web.service;

import cn.dev33.satoken.secure.BCrypt;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.model.RegisterBody;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.exception.user.CaptchaException;
import com.yiruantong.common.core.exception.user.CaptchaExpireException;
import com.yiruantong.common.core.exception.user.UserException;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.ServletUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.log.event.LogininforEvent;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.system.domain.tenant.bo.SysTenantProfileBo;
import com.yiruantong.system.service.tenant.ISysTenantProfileService;
import org.springframework.stereotype.Service;

/**
 * 注册校验方法
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysRegisterService {
  private final ISysTenantProfileService sysTenantProfileService;

  /**
   * 注册
   */
  public void register(RegisterBody registerBody) {
    String nickName = registerBody.getNickName();
    String phoneNumber = registerBody.getPhoneNumber();
    String password = TenantConstants.DEFAULT_TENANT_ID; // 密码默认为6个0

    // 验证码开关
    validateCaptcha(phoneNumber, registerBody.getCode());
    SysTenantProfileBo profileBo = new SysTenantProfileBo();
    profileBo.setPassword(BCrypt.hashpw(password));
    profileBo.setEnable(EnableEnum.ENABLE.getId());
    profileBo.setNickName(nickName);
    profileBo.setUserName(phoneNumber);
    profileBo.setPhoneNumber(phoneNumber);
    profileBo.setCompanyName(registerBody.getCompanyName());

    if (!sysTenantProfileService.checkPhoneUnique(profileBo)) {
      throw new UserException("user.register.save.error", nickName);
    }
    boolean regFlag = sysTenantProfileService.registerUser(profileBo);
    if (!regFlag) {
      throw new UserException("user.register.error");
    }
    recordLogininfor(nickName, MessageUtils.message("user.register.success"));
  }

  /**
   * 校验验证码
   *
   * @param phoneNumber 用户名
   * @param code        验证码
   */
  public void validateCaptcha(String phoneNumber, String code) {
    String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + phoneNumber;
    String captcha = RedisUtils.getCacheObject(verifyKey);
    RedisUtils.deleteObject(verifyKey);
    if (captcha == null) {
      recordLogininfor(phoneNumber, MessageUtils.message("user.jcaptcha.expire"));
      throw new CaptchaExpireException();
    }
    if (!code.equalsIgnoreCase(captcha)) {
      recordLogininfor(phoneNumber, MessageUtils.message("user.jcaptcha.error"));
      throw new CaptchaException();
    }
  }

  /**
   * 记录登录信息
   *
   * @param nickName 用户名
   * @param status   状态
   */
  private void recordLogininfor(String nickName, String status) {
    LogininforEvent logininforEvent = new LogininforEvent();
    logininforEvent.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
    logininforEvent.setUsername(nickName);
    logininforEvent.setStatus(status);
    logininforEvent.setMessage(Constants.REGISTER);
    logininforEvent.setRequest(ServletUtils.getRequest());
    SpringUtils.context().publishEvent(logininforEvent);
  }

}

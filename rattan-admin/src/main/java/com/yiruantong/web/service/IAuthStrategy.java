package com.yiruantong.web.service;


import com.yiruantong.common.core.domain.model.LoginBody;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.web.domain.vo.LoginVo;

/**
 * 授权策略
 *
 * @author YiRuanTong
 */
public interface IAuthStrategy {

  String BASE_NAME = "AuthStrategy";

  /**
   * 登录
   */
  static LoginVo login(LoginBody loginBody, SysClient client) {
    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    String beanName = grantType + BASE_NAME;
    if (!SpringUtils.containsBean(beanName)) {
      throw new ServiceException("授权类型不正确!");
    }
    IAuthStrategy instance = SpringUtils.getBean(beanName);
    instance.validate(loginBody);
    return instance.login(clientId, loginBody, client);
  }

  /**
   * profile登录
   */
  static LoginVo loginProfile(LoginBody loginBody, SysClient client) {
    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    String beanName = grantType + BASE_NAME;
    if (!SpringUtils.containsBean(beanName)) {
      throw new ServiceException("授权类型不正确!");
    }
    IAuthStrategy instance = SpringUtils.getBean(beanName);
    instance.validate(loginBody);
    return instance.loginProfile(clientId, loginBody, client);
  }

  /**
   * 单点登录
   */
  static LoginVo loginSso(LoginBody loginBody, SysClient client) {
    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    String beanName = grantType + BASE_NAME;
    if (!SpringUtils.containsBean(beanName)) {
      throw new ServiceException("授权类型不正确!");
    }
    IAuthStrategy instance = SpringUtils.getBean(beanName);
//    instance.validate(loginBody);
    return instance.loginSso(clientId, loginBody, client);
  }

  /**
   * 参数校验
   */
  void validate(LoginBody loginBody);

  /**
   * 登录
   */
  LoginVo login(String clientId, LoginBody loginBody, SysClient client);

  /**
   * profile登录
   */
  default LoginVo loginProfile(String clientId, LoginBody loginBody, SysClient client) {
    return null;
  }

  /**
   * 单点登录
   */
  default LoginVo loginSso(String clientId, LoginBody loginBody, SysClient client) {
    return null;
  }

  ;
}

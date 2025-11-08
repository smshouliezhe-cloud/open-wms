package com.yiruantong.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.sso.config.SaSsoServerConfig;
import cn.dev33.satoken.sso.processor.SaSsoServerProcessor;
import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.sso.util.SaSsoConsts;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.yiruantong.common.core.utils.ServletUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Sa-Token-SSO Server端 Controller
 */
@RestController
public class SsoServerController {

  // SSO-Server：统一认证地址
  @SaIgnore
  @RequestMapping("/sso/auth")
  public Object ssoAuth() {
    return SaSsoServerProcessor.instance.ssoAuth();
  }

  // SSO-Server：RestAPI 登录接口
  @SaIgnore
  @RequestMapping("/sso/doLogin")
  public Object ssoDoLogin() {
    return SaSsoServerProcessor.instance.ssoDoLogin();
  }

  // SSO-Server：校验ticket 获取账号id
  @SaIgnore
  @RequestMapping("/sso/checkTicket")
  public Object ssoCheckTicket() {
    String url = "";
    if (ObjectUtil.isNotNull(ServletUtils.getRequest())) {
      url = ServletUtils.getRequest().getRequestURL() + "?" + ServletUtils.getRequest().getQueryString();
    }
    System.out.println("------服务器端ssoCheckTicket接收..." + url);
    SaResult result = (SaResult) SaSsoServerProcessor.instance.ssoCheckTicket();
    Map<String, Object> map = new HashMap<>();
    String loginId = Convert.toStr(result.getData());
    String access_token = StpUtil.getTokenValueByLoginId(loginId);
    map.put("access_token", access_token);
    result.setData(map);
    System.out.println("------服务器端ssoCheckTicket结果：map=" + JsonUtils.toJsonString(map));
    return result;
  }

  // SSO-Server：单点注销
  @SaIgnore
  @RequestMapping("/sso/signout")
  public Object ssoSignout() {
    return SaSsoServerProcessor.instance.ssoSignout();
  }

  /**
   * h5获取 redirectUrl
   */
  @RequestMapping("/sso/getRedirectUrl")
  public SaResult getRedirectUrl(@RequestBody Map<String, Object> map) {
    String redirect = Convert.toStr(map.get("redirect"));
    String mode = Convert.toStr(map.get("mode"));
    String client = Convert.toStr(map.get("client"));

    // 未登录情况下，返回 code=401
    if (!StpUtil.isLogin()) {
      return SaResult.code(401);
    }
    // 已登录情况下，构建 redirectUrl
    redirect = SaFoxUtil.decoderUrl(redirect);
    if (SaSsoConsts.MODE_SIMPLE.equals(mode)) {
      // 模式一
      SaSsoUtil.checkRedirectUrl(redirect);
      return SaResult.data(redirect);
    } else {
      // 模式二或模式三
      String redirectUrl = SaSsoUtil.buildRedirectUrl(StpUtil.getLoginId(), client, redirect);

      return SaResult.data(redirectUrl);
    }
  }

  // 配置SSO相关参数
  @Autowired
  private void configSso(SaSsoServerConfig ssoServer) {
    // 配置 Http 请求处理器 （在模式三的单点注销功能下用到，如不需要可以注释掉）
    ssoServer.sendHttp = url -> {
      try {
        System.out.println("------SsoServer发起请求：" + url);
        String resStr = HttpUtil.get(url);
        System.out.println("------SsoServer请求结果：" + resStr);
        return resStr;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    };
  }
}

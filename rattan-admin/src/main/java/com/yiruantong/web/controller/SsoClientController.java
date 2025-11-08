package com.yiruantong.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.sso.SaSsoManager;
import cn.dev33.satoken.sso.config.SaSsoClientConfig;
import cn.dev33.satoken.sso.model.SaCheckTicketResult;
import cn.dev33.satoken.sso.processor.SaSsoClientProcessor;
import cn.dev33.satoken.sso.template.SaSsoUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Sa-Token-SSO Client端 Controller
 *
 * @author click33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/sso")
public class SsoClientController {
  // 当前是否登录
  @SaIgnore
  @RequestMapping("/isLogin")
  public Object isLogin() {
    return SaResult.data(StpUtil.isLogin());
  }

  // 返回SSO认证中心登录地址
  @SaIgnore
  @RequestMapping("/getSsoAuthUrl")
  public SaResult getSsoAuthUrl(@RequestBody Map<String, Object> map) {
    SaSsoManager.getClientConfig().authUrl = "/login";
    String clientLoginUrl = Convert.toStr(map.get("clientLoginUrl"));
    String serverAuthUrl = SaSsoUtil.buildServerAuthUrl(clientLoginUrl, "");
    return SaResult.data(serverAuthUrl);
  }

  // 根据ticket进行登录
  /*
   * SSO-Client端：处理所有SSO相关请求
   * 		http://{host}:{port}/sso/login			-- Client端登录地址，接受参数：back=登录后的跳转地址
   * 		http://{host}:{port}/sso/logout			-- Client端单点注销地址（isSlo=true时打开），接受参数：back=注销后的跳转地址
   * 		http://{host}:{port}/sso/logoutCall		-- Client端单点注销回调地址（isSlo=true时打开），此接口为框架回调，开发者无需关心
   */

//  @SaIgnore
//  @RequestMapping("/*")
//  public Object ssoRequest() {
//    return SaSsoClientProcessor.instance.dister();
//  }

  @SaIgnore
  @RequestMapping("/doLoginByTicket")
  public SaResult doLoginByTicket(@RequestBody Map<String, Object> map) {
    String ticket = Convert.toStr(map.get("ticket"));
    SaSsoManager.getClientConfig().checkTicketUrl = (SpringUtils.isProd() ? "/prod-api" : "/dev-api") + "/sso/checkTicket";
    SaCheckTicketResult ctr = SaSsoClientProcessor.instance.checkTicket(ticket, "/sso/doLoginByTicket");
    return ctr.result;
  }

  @RequestMapping("/logout")
  public Object logout() {
    SaSsoManager.getClientConfig().sloUrl = (SpringUtils.isProd() ? "/prod-api" : "/dev-api") + "/sso/signout";
    SaResult result = (SaResult) SaSsoClientProcessor.instance.ssoLogout();
    return result;
  }

  @SaIgnore
  @RequestMapping("/logoutCall")
  public Object logoutCall() {
    SaResult result = (SaResult) SaSsoClientProcessor.instance.ssoLogoutCall();
    return result;
  }

  // 配置SSO相关参数
  @Autowired
  private void configSso(SaSsoClientConfig ssoClient) {
    // 配置Http请求处理器
    ssoClient.sendHttp = url -> {
      System.out.println("------ 客户端configSso发起请求：" + url);
      String resStr = HttpUtil.get(url);
      System.out.println("------ 客户端configSso请求结果：" + resStr);
      return resStr;
    };
  }

  // 查询我的账号信息
  @RequestMapping("/myInfo")
  public Object myInfo() {
    // 组织请求参数
    Map<String, Object> map = new HashMap<>();
    map.put("apiType", "userinfo");
    map.put("loginId", StpUtil.getLoginId());

    // 发起请求
    Object resData = SaSsoUtil.getData(map);
    System.out.println("sso-server 返回的信息：" + resData);
    return resData;
  }

  // 全局异常拦截
  @ExceptionHandler
  public SaResult handlerException(Exception e) {
    e.printStackTrace();
    return SaResult.error(e.getMessage());
  }
}

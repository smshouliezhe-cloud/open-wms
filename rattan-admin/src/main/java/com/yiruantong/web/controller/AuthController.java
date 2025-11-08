package com.yiruantong.web.controller;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.sign.SaSignUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginBody;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.domain.model.RegisterBody;
import com.yiruantong.common.core.enums.user.GrantTypeEnum;
import com.yiruantong.common.core.exception.base.CommonException;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.encrypt.utils.EncryptUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.social.config.properties.SocialLoginConfigProperties;
import com.yiruantong.common.social.config.properties.SocialProperties;
import com.yiruantong.common.social.utils.SocialUtils;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.tenant.SysClient;
import com.yiruantong.system.domain.tenant.SysTenant;
import com.yiruantong.system.domain.tenant.vo.SysTenantVo;
import com.yiruantong.system.service.core.ISysConfigService;
import com.yiruantong.system.service.permission.ISysPermissionService;
import com.yiruantong.system.service.permission.ISysSocialService;
import com.yiruantong.system.service.task.ITaskConfigService;
import com.yiruantong.system.service.tenant.ISysClientService;
import com.yiruantong.system.service.tenant.ISysTenantService;
import com.yiruantong.web.domain.vo.LoginTenantVo;
import com.yiruantong.web.domain.vo.LoginVo;
import com.yiruantong.web.domain.vo.TenantListVo;
import com.yiruantong.web.service.IAuthStrategy;
import com.yiruantong.web.service.SysLoginService;
import com.yiruantong.web.service.SysRegisterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.serial.SerialException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 认证
 *
 * @author YiRuanTong
 */
@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final SocialProperties socialProperties;
  private final SysLoginService loginService;
  private final SysRegisterService registerService;
  private final ISysConfigService configService;
  private final ISysTenantService sysTenantService;
  private final ISysSocialService socialUserService;
  private final ISysClientService clientService;
  private final ISysPermissionService sysPermissionService;
  final private ITaskConfigService taskConfigService;

  @Value("${api-decrypt.privateKey}")
  private String privateKey;

  @Value("${api-decrypt.publicKey}")
  private String publicKey;

  /**
   * 登录方法
   *
   * @param loginBody 登录信息
   * @return 结果
   */
  @PostMapping("/login")
  public R<LoginVo> login(@Validated @RequestBody LoginBody loginBody, @RequestHeader("encrypt-key") String encryptKey) {
    checkEncrypt(encryptKey);

    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    SysClient client = clientService.queryByClientId(clientId);
    // 查询不到 client 或 client 内不包含 grantType
    if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
      log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
      return R.fail(MessageUtils.message("auth.grant.type.error"));
    }
    // 校验租户
    loginService.checkTenant(loginBody.getTenantId());
    // 登录
    return R.ok(IAuthStrategy.login(loginBody, client));
  }

  /**
   * 登录方法
   *
   * @param loginBody 登录信息
   * @return 结果
   */
  @PostMapping("/loginGoView")
  public R<LoginVo> loginGoView(@Validated @RequestBody LoginBody loginBody) {
    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    SysClient client = clientService.queryByClientId(clientId);
    // 查询不到 client 或 client 内不包含 grantType
    if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
      log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
      return R.fail(MessageUtils.message("auth.grant.type.error"));
    }
    // 校验租户
    loginService.checkTenant(loginBody.getTenantId());
    // 登录
    return R.ok(IAuthStrategy.login(loginBody, client));
  }

  /**
   * 登录方法
   *
   * @param loginBody 登录信息
   * @return 结果
   */
  @PostMapping("/loginProfile")
  public R<LoginVo> loginProfile(@Validated @RequestBody LoginBody loginBody, @RequestHeader("encrypt-key") String encryptKey) {
    checkEncrypt(encryptKey);

    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    SysClient client = clientService.queryByClientId(clientId);
    // 查询不到 client 或 client 内不包含 grantType
    if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
      log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
      return R.fail(MessageUtils.message("auth.grant.type.error"));
    }
    // 登录
    return R.ok(IAuthStrategy.loginProfile(loginBody, client));
  }

  /**
   * 登录方法
   *
   * @param loginBody 登录信息
   * @return 结果
   */
  @PostMapping("/loginSso")
  public R<LoginVo> loginSso(@Validated @RequestBody LoginBody loginBody) {
    // 授权类型和客户端id
    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    SysClient client = clientService.queryByClientId(clientId);
    // 查询不到 client 或 client 内不包含 grantType
    if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
      log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
      return R.fail(MessageUtils.message("auth.grant.type.error"));
    }
    // 校验租户
    loginService.checkTenant(loginBody.getTenantId());
    // 登录
    return R.ok(IAuthStrategy.loginSso(loginBody, client));
  }

  /**
   * 获取临时token，用于免登录（实现自动登录）
   *
   * @param tokenType 登录类型
   * @return 结果
   */
  @PostMapping("/getTempToken/{tokenType}")
  public R<Map<String, String>> getTempToken(@PathVariable String tokenType, @RequestParam String redirect, @RequestHeader String clientid) {
    Map<String, String> map = new HashMap<>();
    map.put("tenantId", LoginHelper.getTenantId());
    map.put("tokenType", tokenType);
    map.put("clientid", clientid);
    map.put("redirect", redirect);
    map.put("username", "admin");
    map.put("phoneNumber", "18600336433");
    String token = SaTempUtil.createToken(map, 7 * 24 * 60 * 60); // 有效期7天

    Map<String, String> result = new HashMap<>();
    result.put("token", token);
    return R.ok(result);
  }

  /**
   * 临时登录
   *
   * @param token 登录信息
   * @return 结果
   */
  @PostMapping("/tempLogin/{token}")
  public R<LoginVo> tempLogin(@PathVariable String token) throws SerialException {
    Map<String, String> map = new HashMap<>();
    // 解析 token 获取 value，并转换为指定类型
    map = SaTempUtil.parseToken(token, map.getClass());
    Assert.isFalse(ObjectUtil.isNull(map), "token失效，请重新登录系统！");

    // 授权类型和客户端id
    LoginBody loginBody = new LoginBody();
    loginBody.setTenantId(map.get("tenantId"));
    loginBody.setClientId(ServletUtils.getHeader("clientid"));
    loginBody.setUsername(map.get("username"));
    loginBody.setPhoneNumber(map.get("phoneNumber"));
    loginBody.setRedirect(map.get("redirect"));
    loginBody.setGrantType(GrantTypeEnum.PASSWORD.getType());

    String clientId = loginBody.getClientId();
    String grantType = loginBody.getGrantType();
    SysClient client = clientService.queryByClientId(clientId);
    // 查询不到 client 或 client 内不包含 grantType
    if (ObjectUtil.isNull(client) || !StringUtils.contains(client.getGrantType(), grantType)) {
      log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType);
      return R.fail(MessageUtils.message("auth.grant.type.error"));
    }
    // 校验租户
    loginService.checkTenant(loginBody.getTenantId());
    // 登录
    return R.ok(IAuthStrategy.loginSso(loginBody, client));
  }

  /**
   * 校验是否接口加密
   *
   * @param encryptKey 密文
   */
  private void checkEncrypt(String encryptKey) {
    boolean isEncrypt;
    try {
      // 获取 AES 密码 采用 RSA 加密
      String decryptAes = EncryptUtils.decryptByRsa(encryptKey, privateKey);
      // 解密 AES 密码
      String aesPassword = EncryptUtils.decryptByBase64(decryptAes);
      isEncrypt = StringUtils.isNotEmpty(aesPassword);
    } catch (Exception e) {
      isEncrypt = false;
    }
    if (!isEncrypt) {
      throw new CommonException("auth.api.not.encrypt");
    }
  }

  /**
   * 第三方登录请求
   *
   * @param source 登录来源
   * @return 结果
   */
  @GetMapping("/binding/{source}")
  public R<String> authBinding(@PathVariable("source") String source) {
    SocialLoginConfigProperties obj = socialProperties.getType().get(source);
    if (ObjectUtil.isNull(obj)) {
      return R.fail(source + "平台账号暂不支持");
    }
    AuthRequest authRequest = SocialUtils.getAuthRequest(source, socialProperties);
    String authorizeUrl = authRequest.authorize(AuthStateUtils.createState());
    return R.ok("操作成功", authorizeUrl);
  }

  /**
   * 第三方登录回调业务处理 绑定授权
   *
   * @param loginBody 请求体
   * @return 结果
   */
  @PostMapping("/social/callback")
  public R<Void> socialCallback(@RequestBody LoginBody loginBody) {
    // 获取第三方登录信息
    AuthResponse<AuthUser> response = SocialUtils.loginAuth(loginBody, socialProperties);
    AuthUser authUserData = response.getData();
    // 判断授权响应是否成功
    if (!response.ok()) {
      return R.fail(response.getMsg());
    }
    loginService.socialRegister(authUserData);
    return R.ok();
  }


  /**
   * 取消授权
   *
   * @param socialId socialId
   */
  @DeleteMapping(value = "/unlock/{socialId}")
  public R<Void> unlockSocial(@PathVariable Long socialId) {
    Boolean rows = socialUserService.deleteWithValidById(socialId);
    return rows ? R.ok() : R.fail("取消授权失败");
  }

  /**
   * 退出登录
   */
  @PostMapping("/logout")
  public R<Void> logout() {
    loginService.logout();
    return R.ok("退出成功");
  }

  /**
   * 用户注册
   */
  @PostMapping("/register")
  public R<Void> register(@Validated @RequestBody RegisterBody user) {
    if (!configService.registerEnabled()) {
      return R.fail("当前系统没有开启注册功能！");
    }
    registerService.register(user);
    return R.ok("注册成功，默认密码(6个0)：000000");
  }

  /**
   * 登录页面租户下拉框
   *
   * @return 租户列表
   */
  @GetMapping("/tenant/list")
  public R<LoginTenantVo> tenantList(HttpServletRequest request) throws Exception {
    LambdaQueryWrapper<SysTenant> sysTenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
    sysTenantLambdaQueryWrapper.eq(SysTenant::getStatus, 1);
    List<SysTenantVo> tenantList = sysTenantService.selectList(sysTenantLambdaQueryWrapper);
    List<TenantListVo> voList = MapstructUtils.convert(tenantList, TenantListVo.class);
    // 获取域名
    String host;
    String referer = request.getHeader("referer");
    if (StringUtils.isNotBlank(referer)) {
      // 这里从referer中取值是为了本地使用hosts添加虚拟域名，方便本地环境调试
      host = referer.split("//")[1].split("/")[0];
    } else {
      host = new URL(request.getRequestURL().toString()).getHost();
    }
    // 根据域名进行筛选
    List<TenantListVo> list = StreamUtils.filter(voList, vo -> StringUtils.equals(vo.getDomain(), host));
    // 返回对象
    LoginTenantVo vo = new LoginTenantVo();
    vo.setVoList(CollUtil.isNotEmpty(list) ? list : voList);
    vo.setTenantEnabled(TenantHelper.isEnable());
    return R.ok(vo);
  }

  /**
   * 获取账套信息
   *
   * @return 租户列表
   */
  @PostMapping("/tenant/getTenantInfo")
  public R<Map<String, Object>> getTenantInfo(HttpServletRequest request) throws URISyntaxException {
    return R.ok(sysTenantService.getTenantInfo(request));
  }

  /**
   * 邮件激活
   *
   * @return 租户列表
   */
  @GetMapping("/emailActivate")
  public String emailActivate(@RequestParam String key) {
    return sysPermissionService.emailActivate(key);
  }

  /**
   * 解密
   *
   * @return 返回解密数据
   */
  @PostMapping("/decrypt")
  public R<Map<String, String>> decrypt(@RequestBody Map<String, Object> map) {
    Map<String, String> resultMap = new HashMap<>();
    String decryptText = "";
    try {
      String encryptText = Convert.toStr(map.get("encryptText"));
      decryptText = EncryptUtils.decryptByRsa(encryptText, privateKey);
    } catch (Exception ignored) {
    }

    resultMap.put("decryptText", decryptText);
    return R.ok(resultMap);
  }

  /**
   * 模拟登录
   */
  @GetMapping("/testLogin/{tenantId}/{userName}")
  public R<Void> testLogin(@PathVariable String tenantId, @PathVariable String userName) {
    if (StringUtils.isEmpty(userName)) {
      userName = "api001";
    }
    if (StringUtils.isEmpty(tenantId)) {
      tenantId = TenantConstants.DEFAULT_TENANT_ID;
    }
    LoginBody loginBody = new LoginBody();
    loginBody.setTenantId(tenantId);
    loginBody.setClientId("6da13b696f737097e0146e47cc0d0985");
    loginBody.setGrantType("password");
    loginBody.setUsername(userName);
    loginBody.setPassword(TenantConstants.DEFAULT_TENANT_ID);

    String url = "http://127.0.0.1:8800/dev-api/auth/login";
    // 生成一个 AES 密钥
    String aesKey = RandomUtil.randomString(32);
    String encodedString = EncryptUtils.encryptByBase64(aesKey); // 加密base64
    String encryptKey = EncryptUtils.encryptByRsa(encodedString, publicKey);
    String body = EncryptUtils.encryptByAes(JsonUtils.toJsonString(loginBody), aesKey); // 加密内容
    String resultBody = HttpUtil.createPost(url)
      .header("encrypt-key", encryptKey)
      .header("Content-Type", "application/json;charset=UTF-8")
      .body(body).execute().body();

    return R.ok(resultBody);
  }

  /**
   * 获取签名信息
   *
   * @return 签名信息
   */
  @PostMapping("/getSign")
  public R<Map<String, Object>> getSign(@RequestBody Map<String, Object> map) {
    // 自定义API鉴权密码
    SaSignConfig saSignConfig = new SaSignConfig();
    saSignConfig.setSecretKey("eyyVhzKYzJn1eGq0");
    SaManager.getSaSignTemplate().setSignConfig(saSignConfig);
    // 签名请求参数
    // 补全 timestamp、nonce、distributionSign 参数，并序列化为 kv 字符串
    String paramStr = SaSignUtil.addSignParamsAndJoin(map);

    map.put("paramStr", paramStr);
    return R.ok(map);
  }

  /**
   * ERP手工推送测试
   *
   * @return R
   */
  @GetMapping("/erpPush")
  public R<Void> erpPush() {
    String jobParams = "{\"tenantId\":\"7-YHC1PKNK2H\",\"configIds\":\"3\"}";

    String tenantId = JsonUtils.parseObj(jobParams).getStr("tenantId");
    if (StrUtil.isEmpty(tenantId)) tenantId = TenantConstants.DEFAULT_TENANT_ID;
    String configIds = JsonUtils.parseObj(jobParams).getStr("configIds");
    if (StrUtil.isEmpty(configIds)) {
      return R.fail("没有可执行的数据");
    }

    LoginUser loginUser = new LoginUser();
    loginUser.setAdministrator(true);
    loginUser.setUserId(1L);
    loginUser.setNickname("snailjob");
    loginUser.setRoleId(1L);
    loginUser.setTenantId(tenantId);
    List<Long> configIdList = Convert.toList(Long.class, StrUtil.split(configIds, ','));
    taskConfigService.autoPush(configIdList, loginUser);

    return R.ok();
  }
}

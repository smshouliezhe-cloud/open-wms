package com.yiruantong.common.satoken.utils;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.config.SaSignConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.sign.SaSignUtil;
import cn.dev33.satoken.spring.SpringMVCUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.constant.CacheNames;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.tenant.ClientKeyEnum;
import com.yiruantong.common.core.enums.user.UserCacheEnum;
import com.yiruantong.common.core.enums.user.UserTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.redis.utils.CacheUtils;
import com.yiruantong.common.satoken.event.LogoutEvent;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 登录鉴权助手
 * <p>
 * user_type 为 用户类型 同一个用户表 可以有多种用户类型 例如 pc,app
 * deivce 为 设备类型 同一个用户类型 可以有 多种设备类型 例如 web,ios
 * 可以组成 用户类型与设备类型多对多的 权限灵活控制
 * <p>
 * 多用户体系 针对 多种用户类型 但权限控制不一致
 * 可以组成 多用户类型表与多设备类型 分别控制权限
 *
 * @author YiRuanTong
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

  public static final String LOGIN_USER_KEY = "loginUser";
  public static final String TENANT_KEY = "tenantId";
  public static final String IS_ADMINISTRATOR = "isAdministrator";
  public static final String USER_KEY = "userId";
  public static final String USER_TYPE = "userType";
  public static final String PHONE_NUMBER = "phoneNumber";
  public static final String EMAIL = "email";
  public static final String CLIENT_ID = "clientId";
  public static final String CLIENT_KEY = "clientKey";
  public static final String CLIENT_SECRET = "clientSecret";
  public static final String PACKAGE_ID = "packageId";
  public static final String PACKAGE_NAME = "packageName";
  private static final ThreadLocal<LoginUser> TEMP_DYNAMIC_LOGIN_USER = new TransmittableThreadLocal<>();
  // 非web登录

  /**
   * 登录系统 基于 设备类型
   * 针对相同用户体系不同设备
   *
   * @param loginUser 登录用户信息
   */
  public static void login(LoginUser loginUser) {
    LoginHelper.login(loginUser, null);
  }

  /**
   * 登录系统 基于 设备类型
   * 针对相同用户体系不同设备
   *
   * @param loginUser 登录用户信息
   * @param model     配置参数
   */
  public static void login(LoginUser loginUser, SaLoginModel model) {
    SaStorage storage = SaHolder.getStorage();
    storage.set(LOGIN_USER_KEY, loginUser);
    storage.set(TENANT_KEY, loginUser.getTenantId());
    storage.set(IS_ADMINISTRATOR, loginUser.isAdministrator());
    storage.set(USER_KEY, loginUser.getUserId());
    storage.set(USER_TYPE, loginUser.getUserType());
    storage.set(PACKAGE_ID, loginUser.getPackageId());
    storage.set(PACKAGE_NAME, loginUser.getPackageName());
    storage.set(PHONE_NUMBER, loginUser.getPhoneNumber());
    storage.set(EMAIL, loginUser.getEmail());
    model = ObjectUtil.defaultIfNull(model, new SaLoginModel());
    model
      .setExtra(LOGIN_USER_KEY, JSONUtil.toJsonStr(loginUser))
      .setExtra(TENANT_KEY, loginUser.getTenantId())
      .setExtra(IS_ADMINISTRATOR, loginUser.isAdministrator())
      .setExtra(USER_KEY, loginUser.getUserId())
      .setExtra(USER_TYPE, loginUser.getUserType())
      .setExtra(PHONE_NUMBER, loginUser.getPhoneNumber())
      .setExtra(EMAIL, loginUser.getEmail())
      .setExtra(PACKAGE_ID, loginUser.getPackageId())
      .setExtra(PACKAGE_NAME, loginUser.getPackageName());
    StpUtil.login(loginUser.getLoginId(), model);
    StpUtil.getSession().set(LOGIN_USER_KEY, loginUser);
  }

  /**
   * 退出
   * 针对相同用户体系不同设备
   */
  public static void logout() {
    SaStorage storage = SaHolder.getStorage();
    storage.set(LOGIN_USER_KEY, null);
    storage.set(TENANT_KEY, null);
    storage.set(IS_ADMINISTRATOR, null);
    storage.set(USER_KEY, null);
    storage.set(USER_TYPE, null);
    storage.set(PACKAGE_ID, null);
    storage.set(PACKAGE_NAME, null);
    storage.set(PHONE_NUMBER, null);
    storage.set(EMAIL, null);
    StpUtil.logout();
  }

  /**
   * 获取用户(多级缓存)
   */
  public static LoginUser getLoginUser() {
    try {
      if (!SpringMVCUtil.isWeb()) {
        // 非web下获取
        return TEMP_DYNAMIC_LOGIN_USER.get();
      }

      LoginUser loginUser = (LoginUser) SaHolder.getStorage().get(LOGIN_USER_KEY);
      if (loginUser != null) {
        return loginUser;
      }
      String loginUserStr = (String) (StpUtil.getExtra(LOGIN_USER_KEY));
      loginUser = JSONUtil.toBean(loginUserStr, LoginUser.class);
      SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
      if (loginUser != null) {
        return loginUser;
      }

      SaSession session = StpUtil.getSession();
      if (ObjectUtil.isNull(session)) {
        return null;
      }
      loginUser = (LoginUser) session.get(LOGIN_USER_KEY);
      SaHolder.getStorage().set(LOGIN_USER_KEY, loginUser);
      return loginUser;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 设置用户信息缓存
   */
  public static void setLoginUser(LoginUser loginUser) {
    if (!SpringMVCUtil.isWeb()) {
      TEMP_DYNAMIC_LOGIN_USER.set(loginUser);
    } else {
      LoginHelper.login(loginUser);
    }
  }

  /**
   * 获取用户基于token
   */
  public static LoginUser getLoginUser(String token) {
    Object loginId = StpUtil.getLoginIdByToken(token);
    SaSession session = StpUtil.getSessionByLoginId(loginId);
    if (ObjectUtil.isNull(session)) {
      return null;
    }
    return (LoginUser) session.get(LOGIN_USER_KEY);
  }

  /**
   * 是否超级管理员
   */
  public static boolean isAdministrator() {
    Boolean isAdminstrator;
    try {
      isAdminstrator = Convert.toBool(SaHolder.getStorage().get(IS_ADMINISTRATOR));
      if (ObjectUtil.isNull(isAdminstrator)) {
        isAdminstrator = Convert.toBool(StpUtil.getExtra(IS_ADMINISTRATOR));
        SaHolder.getStorage().set(IS_ADMINISTRATOR, isAdminstrator);
      }
    } catch (Exception e) {
      return false;
    }
    return isAdminstrator;
  }

  /**
   * 获取用户id
   */
  public static Long getUserId() {
    Long userId;
    try {
      userId = Convert.toLong(SaHolder.getStorage().get(USER_KEY));
      if (ObjectUtil.isNull(userId)) {
        userId = Convert.toLong(StpUtil.getExtra(USER_KEY));
        SaHolder.getStorage().set(USER_KEY, userId);
      }
    } catch (Exception e) {
      return null;
    }
    return userId;
  }

  /**
   * 获取电话号码
   */
  public static String getPhoneNumber() {
    String phoneNumber;
    try {
      phoneNumber = Convert.toStr(SaHolder.getStorage().get(PHONE_NUMBER));
      if (ObjectUtil.isNull(phoneNumber)) {
        phoneNumber = Convert.toStr(StpUtil.getExtra(PHONE_NUMBER));
        SaHolder.getStorage().set(PHONE_NUMBER, phoneNumber);
      }
    } catch (Exception e) {
      return null;
    }
    return phoneNumber;
  }

  /**
   * 获取email
   */
  public static String getEmail() {
    String email;
    try {
      email = Convert.toStr(SaHolder.getStorage().get(EMAIL));
      if (ObjectUtil.isNull(email)) {
        email = Convert.toStr(StpUtil.getExtra(EMAIL));
        SaHolder.getStorage().set(EMAIL, email);
      }
    } catch (Exception e) {
      return null;
    }
    return email;
  }

  /**
   * 套餐id
   */
  public static Long getPackageId() {
    Long packageId;
    try {
      packageId = Convert.toLong(SaHolder.getStorage().get(PACKAGE_ID));
      if (ObjectUtil.isNull(packageId)) {
        packageId = Convert.toLong(StpUtil.getExtra(PACKAGE_ID));
        SaHolder.getStorage().set(PACKAGE_ID, packageId);
      }
    } catch (Exception e) {
      return null;
    }
    return packageId;
  }

  /**
   * 获取租户ID
   */
  public static String getTenantId() {
    String tenantId;
    try {
      if (!SpringMVCUtil.isWeb()) {
        // 非web下获取
        return TEMP_DYNAMIC_LOGIN_USER.get().getTenantId();
      }

      tenantId = (String) SaHolder.getStorage().get(TENANT_KEY);
      if (ObjectUtil.isNull(tenantId)) {
        tenantId = (String) StpUtil.getExtra(TENANT_KEY);
        SaHolder.getStorage().set(TENANT_KEY, tenantId);
      }
    } catch (Exception e) {
      return null;
    }
    return tenantId;
  }

  /**
   * 获取部门ID
   */
  public static Long getDeptId() {
    return Optional.ofNullable(getLoginUser()).map(LoginUser::getDeptId).orElse(null);
  }

  /**
   * 获取部门
   */
  public static String getDeptName() {
    return Optional.ofNullable(getLoginUser()).map(LoginUser::getDeptName).orElse(null);
  }

  /**
   * 获取用户账户
   */
  public static String getUsername() {
    return Optional.ofNullable(getLoginUser()).map(LoginUser::getUsername).orElse(null);
  }

  /**
   * 获取用户昵称
   */
  public static String getNickname() {
    return Optional.ofNullable(getLoginUser()).map(LoginUser::getNickname).orElse(null);
  }

  /**
   * 获取用户类型
   */
  public static UserTypeEnum getUserType() {
    String loginType = StpUtil.getLoginIdAsString();
    return UserTypeEnum.getUserType(loginType);
  }

  /**
   * 是否为超级管理员
   *
   * @param userId 用户ID
   * @return 结果
   */
  public static boolean isSuperAdmin(Long userId) {
    if (ObjectUtil.isNull(getLoginUser())) return false;
    return getLoginUser().isAdministrator();
  }

  /**
   * 是否为超级管理员
   *
   * @return 结果
   */
  public static boolean isSuperAdmin() {
    if (ObjectUtil.isNull(getLoginUser())) return false;
    return getLoginUser().isAdministrator();
  }

  /**
   * 是否为超级管理员
   *
   * @param rolePermission 角色权限标识组
   * @return 结果
   */
  public static boolean isTenantAdmin(Set<String> rolePermission) {
    if (CollUtil.isEmpty(rolePermission)) {
      return false;
    }
    return rolePermission.contains(TenantConstants.TENANT_ADMIN_ROLE_KEY);
  }

  /**
   * 是否为超级管理员
   *
   * @return 结果
   */
  public static boolean isTenantAdmin() {
    return isTenantAdmin(Objects.requireNonNull(getLoginUser()).getRolePermission());
  }

  /**
   * 签名校验
   */
  public static void checkSign() {
    String clientKey = Convert.toStr(StpUtil.getExtra(LoginHelper.CLIENT_KEY));
    if (ClientKeyEnum.matchingEnum(clientKey) == ClientKeyEnum.API) {
      UserTypeEnum userType = getUserType();
      if (userType != UserTypeEnum.API_USER) {
        SpringUtils.context().publishEvent(new LogoutEvent());
        throw new ServiceException("user.api.not.login.api");
      }

      // 设置签名密码
      String clientSecret = Convert.toStr(StpUtil.getExtra(LoginHelper.CLIENT_SECRET));
      SaSignConfig saSignConfig = new SaSignConfig();
      saSignConfig.setSecretKey(clientSecret);
      SaManager.getSaSignTemplate().setSignConfig(saSignConfig);

      // 验证密码
      SaSignUtil.checkRequest(SaHolder.getRequest());
    }
  }

  /**
   * 校验用户类型，API不需要登录控制台
   */
  public static void checkConsoleUser() {
    String clientKey = StpUtil.getExtra(LoginHelper.CLIENT_KEY).toString();
    if (ClientKeyEnum.matchingEnum(clientKey) == ClientKeyEnum.API || getUserType() == UserTypeEnum.API_USER) {
      SpringUtils.context().publishEvent(new LogoutEvent());
      throw new ServiceException("user.api.not.login.api");
    }
  }

  /**
   * 检查当前用户是否已登录
   *
   * @return 结果
   */
  public static boolean isLogin() {
    try {
      return getLoginUser() != null;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 设置用户级别的缓存数据
   */
  public static void setUserCache(UserCacheEnum userCacheEnum, Map<String, Object> map) {
    Assert.isFalse(userCacheEnum == null, "用户缓存关键词枚举不存在！");
    String key = userCacheEnum.getName() + ":" + LoginHelper.getUserId();
    if (map == null) {
      CacheUtils.evict(CacheNames.SYS_USER_DATA, key);
    } else {
      CacheUtils.put(CacheNames.SYS_USER_DATA, key, map);
    }
  }

  /**
   * 获取用户级别的缓存数据
   *
   * @return 结果
   */
  public static Map<String, Object> getUserCache(UserCacheEnum userCacheEnum) {
    try {
      String key = userCacheEnum.getName() + ":" + LoginHelper.getUserId();
      return CacheUtils.get(CacheNames.SYS_USER_DATA, key);
    } catch (Exception e) {
      return null;
    }
  }
}

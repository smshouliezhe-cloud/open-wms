package com.yiruantong.web.service;

import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.tms.BaseCarrier;
import com.yiruantong.basic.domain.tms.BaseDriver;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.dto.PostDTO;
import com.yiruantong.common.core.domain.dto.RoleDTO;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.user.LoginTypeEnum;
import com.yiruantong.common.core.enums.user.TenantStatusEnum;
import com.yiruantong.common.core.exception.user.UserException;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.log.event.LogininforEvent;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.event.LogoutEvent;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.exception.TenantException;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.permission.bo.SysSocialBo;
import com.yiruantong.system.domain.permission.vo.SysSocialVo;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.vo.SysTenantVo;
import com.yiruantong.system.mapper.permission.SysUserMapper;
import com.yiruantong.system.service.permission.ISysPermissionService;
import com.yiruantong.system.service.permission.ISysSocialService;
import com.yiruantong.system.service.permission.ISysUserService;
import com.yiruantong.system.service.tenant.ISysTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.function.Supplier;

/**
 * 登录校验方法
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysLoginService {

  private final ISysTenantService tenantService;
  private final ISysPermissionService permissionService;
  private final ISysSocialService sysSocialService;
  private final SysUserMapper userMapper;
  private final ISysUserService sysUserService;
  private final IBaseConsignorService baseConsignorService;
  @Value("${user.password.maxRetryCount}")
  private Integer maxRetryCount;
  @Value("${user.password.lockTime}")
  private Integer lockTime;

  /**
   * 绑定第三方用户
   *
   * @param authUserData 授权响应实体
   * @return 统一响应实体
   */
  public void socialRegister(AuthUser authUserData) {
    String authId = authUserData.getSource() + authUserData.getUuid();
    // 第三方用户信息
    SysSocialBo bo = BeanUtil.toBean(authUserData, SysSocialBo.class);
    BeanUtil.copyProperties(authUserData.getToken(), bo);
    bo.setUserId(LoginHelper.getUserId());
    bo.setAuthId(authId);
    bo.setOpenId(authUserData.getUuid());
    bo.setUserName(authUserData.getUsername());
    bo.setNickName(authUserData.getNickname());
    // 查询是否已经绑定用户
    SysSocialVo vo = sysSocialService.selectByAuthId(authId);
    if (ObjectUtil.isEmpty(vo)) {
      // 没有绑定用户, 新增用户信息
      sysSocialService.insertByBo(bo);
    } else {
      // 更新用户信息
      bo.setId(vo.getId());
      sysSocialService.updateByBo(bo);
    }
  }


  /**
   * 退出登录
   */
  public void logout() {
    try {
      LoginUser loginUser = LoginHelper.getLoginUser();
      if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
        // 超级管理员 登出清除动态租户
        TenantHelper.clearDynamic();
      }
      if (loginUser == null) {
        return;
      }
      recordLogininfor(loginUser.getTenantId(), loginUser.getUsername(), Constants.LOGOUT, MessageUtils.message("user.logout.success"));
    } catch (NotLoginException ignored) {
    } finally {
      try {
        LoginHelper.logout();
      } catch (NotLoginException ignored) {
      }
    }
  }

  /**
   * 记录登录信息
   *
   * @param tenantId 租户ID
   * @param username 用户名
   * @param status   状态
   * @param message  消息内容
   */
  public void recordLogininfor(String tenantId, String username, String status, String message) {
    LogininforEvent logininforEvent = new LogininforEvent();
    logininforEvent.setTenantId(tenantId);
    logininforEvent.setUsername(username);
    logininforEvent.setStatus(status);
    logininforEvent.setMessage(message);
    logininforEvent.setRequest(ServletUtils.getRequest());
    SpringUtils.context().publishEvent(logininforEvent);
  }

  /**
   * 构建登录用户
   */
  public LoginUser buildLoginUser(String tenantId, SysUserVo user) {
    SysTenantVo tenant = tenantService.queryByTenantId(tenantId);
    Assert.isFalse(ObjectUtil.isNull(tenant), tenantId + "账号不存在");

    LoginUser loginUser = new LoginUser();
    loginUser.setTenantId(user.getTenantId());
    loginUser.setUserId(user.getUserId());
    loginUser.setDeptId(user.getDeptId());
    loginUser.setUsername(user.getUserName());
    loginUser.setNickname(user.getNickName());
    loginUser.setUserType(user.getUserType());
    loginUser.setAdministrator(B.isEqual(user.getIsAdministrator(), 1));
    loginUser.setMenuPermission(permissionService.getMenuPermission(user.getUserId()));
    loginUser.setRolePermission(permissionService.getRolePermission(user.getUserId()));
    loginUser.setDeptName(ObjectUtil.isNull(user.getDept()) ? "" : user.getDept().getDeptName());
    loginUser.setPackageId(tenant.getPackageId());
    loginUser.setPackageName(tenant.getPackageName());
    loginUser.setPhoneNumber(user.getPhoneNumber());
    loginUser.setEmail(user.getEmail());

    List<RoleDTO> roles = BeanUtil.copyToList(user.getRoles(), RoleDTO.class);
    loginUser.setRoles(roles);

    List<PostDTO> posts = BeanUtil.copyToList(user.getPosts(), PostDTO.class);
    loginUser.setPosts(posts);
    return loginUser;
  }

  /**
   * 构建profile用户
   */
  public LoginUser buildLoginProfile(String tenantId, SysTenantProfile user) {
    SysTenantVo tenant = tenantService.queryByTenantId(tenantId);

    LoginUser loginUser = new LoginUser();
    loginUser.setTenantId("-1");
    loginUser.setUserId(user.getProfileId());
    loginUser.setDeptId(null);
    loginUser.setUsername(user.getUserName());
    loginUser.setNickname(user.getNickName());
    loginUser.setUserType("sys_user");
    loginUser.setAdministrator(false);
    loginUser.setPackageId(tenant.getPackageId());
    loginUser.setPackageName(tenant.getPackageName());
    loginUser.setPhoneNumber(user.getPhoneNumber());
    return loginUser;
  }

  /**
   * 构建登货主录用户
   */
  public LoginUser buildLoginUser(String tenantId, BaseConsignor user) {
    LoginUser loginUser = new LoginUser();
    loginUser.setTenantId(user.getTenantId());
    loginUser.setUserId(user.getConsignorId());
    loginUser.setDeptId(null);
    loginUser.setDeptName(null);
    loginUser.setUsername(user.getConsignorCode());
    loginUser.setNickname(user.getConsignorName());
    loginUser.setUserType("app_consignor");
    loginUser.setRoleName("货主");
    loginUser.setAdministrator(false);
    loginUser.setMenuPermission(null);

    Set<String> set = new HashSet<>();
    loginUser.setRolePermission(set);
    List<RoleDTO> roles = new ArrayList<>();
    var r = new RoleDTO();
    r.setRoleId(1L);
    r.setRoleName("货主角色");
    roles.add(r);
    loginUser.setRoles(roles);
    return loginUser;
  }

  /**
   * 构建司机登录用户
   */
  public LoginUser buildLoginUser(String tenantId, BaseDriver user) {
    LoginUser loginUser = new LoginUser();
    loginUser.setTenantId(user.getTenantId());
    loginUser.setUserId(user.getDriverId());
    loginUser.setDeptId(null);
    loginUser.setDeptName(null);
    loginUser.setUsername(user.getDriverCode());
    loginUser.setNickname(user.getDriverName());
    loginUser.setUserType("app_consignor");
    loginUser.setRoleName("司机");
    loginUser.setAdministrator(false);
    loginUser.setMenuPermission(null);

    Set<String> set = new HashSet<>();
    loginUser.setRolePermission(set);
    List<RoleDTO> roles = new ArrayList<>();
    var r = new RoleDTO();
    r.setRoleId(1L);
    r.setRoleName("司机角色");
    roles.add(r);
    loginUser.setRoles(roles);
    return loginUser;
  }

  /**
   * 构建承运商登录用户
   */
  public LoginUser buildLoginUser(String tenantId, BaseCarrier user) {
    LoginUser loginUser = new LoginUser();
    loginUser.setTenantId(user.getTenantId());
    loginUser.setUserId(user.getCarrierId());
    loginUser.setDeptId(null);
    loginUser.setDeptName(null);
    loginUser.setUsername(user.getCarrierCode());
    loginUser.setNickname(user.getCarrierName());
    loginUser.setUserType("app_consignor");
    loginUser.setRoleName("承运商");
    loginUser.setAdministrator(false);
    loginUser.setMenuPermission(null);

    Set<String> set = new HashSet<>();
    loginUser.setRolePermission(set);
    List<RoleDTO> roles = new ArrayList<>();
    var r = new RoleDTO();
    r.setRoleId(1L);
    r.setRoleName("承运商角色");
    roles.add(r);
    loginUser.setRoles(roles);
    return loginUser;
  }

  /**
   * 记录登录信息
   *
   * @param userId 用户ID
   */
  public void recordLoginInfo(Long userId) {
    LambdaUpdateWrapper<SysUser> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    userLambdaUpdateWrapper
      .set(SysUser::getLoginIp, ServletUtils.getClientIP())
      .set(SysUser::getLoginDate, DateUtils.getNowDate())
      .eq(SysUser::getUserId, userId);
    this.sysUserService.update(userLambdaUpdateWrapper);
  }

  /**
   * 记录货主登录信息
   *
   * @param userId 用户ID
   */
  public void recordLoginInfoConsignor(Long userId) {
    LambdaUpdateWrapper<BaseConsignor> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    userLambdaUpdateWrapper
      .set(BaseConsignor::getLoginIp, ServletUtils.getClientIP())
      .set(BaseConsignor::getLoginDate, DateUtils.getNowDate())
      .eq(BaseConsignor::getConsignorId, userId);
    this.baseConsignorService.update(userLambdaUpdateWrapper);
  }

  /**
   * 登录校验
   */
  public void checkLogin(LoginTypeEnum loginType, String tenantId, String username, Supplier<Boolean> supplier) {
    String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;
    String loginFail = Constants.LOGIN_FAIL;

    // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
    int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
    // 锁定时间内登录 则踢出
    if (errorNumber >= maxRetryCount) {
      recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
      throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
    }

    if (supplier.get()) {
      // 错误次数递增
      errorNumber++;
      RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
      // 达到规定错误次数 则锁定登录
      if (errorNumber >= maxRetryCount) {
        recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
        throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
      } else {
        // 未达到规定错误次数
        recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
        throw new UserException(loginType.getRetryLimitCount(), errorNumber);
      }
    }

    // 登录成功 清空错误次数
    RedisUtils.deleteObject(errorKey);
  }

  /**
   * 登录校验
   */
  public void checkLoginSso(LoginTypeEnum loginType, String tenantId, String username, Supplier<Boolean> supplier) {
    String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username;
    String loginFail = Constants.LOGIN_FAIL;

    // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
    int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
    // 锁定时间内登录 则踢出
    if (errorNumber >= maxRetryCount) {
      recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
      throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
    }

    if (supplier.get()) {
      // 错误次数递增
      errorNumber++;
      RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime));
      // 达到规定错误次数 则锁定登录
      if (errorNumber >= maxRetryCount) {
        recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitExceed(), maxRetryCount, lockTime));
        throw new UserException(loginType.getRetryLimitExceed(), maxRetryCount, lockTime);
      } else {
        // 未达到规定错误次数
        recordLogininfor(tenantId, username, loginFail, MessageUtils.message(loginType.getRetryLimitCount(), errorNumber));
        throw new UserException(loginType.getRetryLimitCount(), errorNumber);
      }
    }

    // 登录成功 清空错误次数
    RedisUtils.deleteObject(errorKey);
  }

  /**
   * 校验租户
   *
   * @param tenantId 租户ID
   */
  public void checkTenant(String tenantId) {
    if (!TenantHelper.isEnable()) {
      return;
    }
    if (TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
      return;
    }
    if (StringUtils.isBlank(tenantId)) {
      throw new TenantException("tenant.number.not.blank");
    }
    SysTenantVo tenant = tenantService.queryByTenantId(tenantId);
    if (ObjectUtil.isNull(tenant)) {
      log.info("登录租户：{} 不存在.", tenantId);
      throw new TenantException("tenant.not.exists");
    } else if (TenantStatusEnum.DISABLE.getCode().equals(tenant.getStatus())) {
      log.info("登录租户：{} 已被停用.", tenantId);
      throw new TenantException("tenant.blocked");
    } else if (ObjectUtil.isNotNull(tenant.getExpireTime()) && new Date().after(tenant.getExpireTime())) {
      log.info("登录租户：{} 已超过有效期.", tenantId);
      throw new TenantException("tenant.expired");
    }
  }

  /**
   * 监听系统退出事件
   *
   * @param logoutEvent 退出参数
   */
  @EventListener
  public void logoutEventListener(LogoutEvent logoutEvent) {
    logout();
  }
}

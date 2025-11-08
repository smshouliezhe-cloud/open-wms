package com.yiruantong.system.service.permission.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.yiruantong.system.service.core.ISysMenuService;
import com.yiruantong.system.service.permission.ISysRoleService;
import com.yiruantong.system.service.permission.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.ModifyPhoneNumberBody;
import com.yiruantong.common.core.exception.user.CaptchaExpireException;
import com.yiruantong.common.core.utils.MessageUtils;
import com.yiruantong.common.core.utils.ServletUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.encrypt.utils.EncryptUtils;
import com.yiruantong.common.mail.config.properties.MailProperties;
import com.yiruantong.common.mail.utils.MailUtils;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.system.service.permission.ISysPermissionService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户权限处理
 *
 * @author rattan
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl implements ISysPermissionService {

  private final ISysRoleService roleService;
  private final ISysMenuService menuService;
  private final MailProperties mailProperties;
  private final ISysUserService sysUserService;
  private final static String PASSWORD = RandomUtil.randomString(32);

  /**
   * 获取角色数据权限
   *
   * @param userId 用户id
   * @return 角色权限信息
   */
  @Override
  public Set<String> getRolePermission(Long userId) {
    Set<String> roles = new HashSet<>();
    // 管理员拥有所有权限
    if (LoginHelper.isSuperAdmin(userId)) {
      roles.add(TenantConstants.SUPER_ADMIN_ROLE_KEY);
    } else {
      roles.addAll(roleService.selectRolePermissionByUserId(userId));
    }
    return roles;
  }

  /**
   * 获取菜单数据权限
   *
   * @param userId 用户id
   * @return 菜单权限信息
   */
  @Override
  public Set<String> getMenuPermission(Long userId) {
    Set<String> perms = new HashSet<>();
    // 管理员拥有所有权限
    if (LoginHelper.isSuperAdmin(userId)) {
      perms.add("*:*:*");
    } else {
      var menuPerms = menuService.selectMenuPermsByUserId(userId);
      if (ObjectUtil.isNotNull(menuPerms)) perms.addAll(menuPerms);
    }
    return perms;
  }

  @Override
  public R<Void> sendActiveEmail() {
    if (!mailProperties.getEnabled()) {
      return R.fail("当前系统没有开启邮箱功能！");
    }
    String email = LoginHelper.getEmail();
    String key = GlobalConstants.CAPTCHA_CODE_KEY + email;
    String code = IdUtil.randomUUID();
    RedisUtils.setCacheObject(key, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION));

    String value = email + "," + code;
    String encryptText = EncryptUtils.encryptByAes(value, PASSWORD);
    String content = String.format("点击此链接进行激活邮件： <a href=\"%s/auth/emailActivate?key=%s\">点我激活</a>", mailProperties.getServerUrl(), ServletUtils.urlEncode(encryptText))
      + "，有效性为" + Constants.CAPTCHA_EXPIRATION + "分钟，请尽快操作。";
    try {
      MailUtils.sendHtml(email, "邮件激活", content);
    } catch (Exception e) {
      log.error("邮件激活发送异常 => {}", e.getMessage());
      return R.fail(e.getMessage());
    }
    return R.ok();
  }

  @Override
  public String emailActivate(String key) {
    try {
      String decryptText = EncryptUtils.decryptByAes(key, PASSWORD);
      if (StringUtils.isEmpty(decryptText)) {
        return "验证失败，key不存在！";
      }
      String[] decryptTexts = StringUtils.split(decryptText, StringUtils.SEPARATOR_COMMA);

      String email = decryptTexts[0];
      String code = decryptTexts[1];
      String cacheKey = GlobalConstants.CAPTCHA_CODE_KEY + email;
      String cacheCode = Convert.toStr(RedisUtils.getCacheObject(cacheKey));
      RedisUtils.deleteObject(cacheKey);
      if (StringUtils.equals(cacheCode, code)) {
        sysUserService.emailActivate(email);
        return MessageUtils.message("user.email.active.success");
      } else {
        return MessageUtils.message("user.email.active.error");
      }
    } catch (Exception e) {
      log.error("邮件激活发送异常 => {}", e.getMessage());
      return "激活异常，请重新激活操作";
    }
  }

  @Override
  public R<Void> sendPhoneActivate(ModifyPhoneNumberBody phoneNumberBody) {
    String phoneNumber = phoneNumberBody.getPhoneNumber();
    String key = GlobalConstants.CAPTCHA_CODE_KEY + phoneNumber;
    String code = RedisUtils.getCacheObject(key);
    if (StringUtils.isBlank(code)) {
      throw new CaptchaExpireException();
    }
    RedisUtils.deleteObject(key);

    if (StringUtils.equals(phoneNumberBody.getCode(), code)) {
      sysUserService.phoneActivate(phoneNumber);
      return R.ok(MessageUtils.message("user.phone.active.success"));
    } else {
      return R.fail(MessageUtils.message("user.phone.active.error"));
    }
  }
}

package com.yiruantong.system.controller.permission;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.yiruantong.system.domain.core.vo.AvatarVo;
import com.yiruantong.system.domain.core.vo.ProfileVo;
import com.yiruantong.system.domain.core.vo.SysOssVo;
import com.yiruantong.system.domain.permission.bo.SysUserBo;
import com.yiruantong.system.domain.permission.bo.SysUserPasswordBo;
import com.yiruantong.system.domain.permission.bo.SysUserPasswordOtherBo;
import com.yiruantong.system.domain.permission.bo.SysUserProfileBo;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import com.yiruantong.system.service.core.ISysOssService;
import com.yiruantong.system.service.permission.ISysUserService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.file.MimeTypeUtils;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.security.utils.PasswordUtils;
import com.yiruantong.common.web.core.BaseController;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * 个人信息 业务处理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/profile")
public class SysProfileController extends BaseController {

  private final ISysUserService userService;
  private final ISysOssService ossService;

  /**
   * 个人信息
   */
  @GetMapping
  public R<ProfileVo> profile() {
    SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
    ProfileVo profileVo = new ProfileVo();
    profileVo.setUser(user);
    profileVo.setRoleGroup(userService.selectUserRoleGroup(user.getUserName()));
    profileVo.setPostGroup(userService.selectUserPostGroup(user.getUserName()));
    return R.ok(profileVo);
  }

  /**
   * 修改用户
   */
  @Log(title = "个人信息", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> updateProfile(@RequestBody SysUserProfileBo profile) {
    SysUserBo user = BeanUtil.toBean(profile, SysUserBo.class);
    if (StringUtils.isNotEmpty(user.getPhoneNumber()) && !userService.checkPhoneUnique(user)) {
      return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
    }
    if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
      return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
    }
    user.setUserId(LoginHelper.getUserId());
    if (userService.updateUserProfile(user) > 0) {
      return R.ok();
    }
    return R.fail("修改个人信息异常，请联系管理员");
  }

  /**
   * 重置密码
   *
   * @param bo 新旧密码
   */
  @Log(title = "个人信息", businessType = BusinessType.UPDATE)
  @PostMapping("/updatePwd")
  public R<Void> updatePwd(@Validated @RequestBody SysUserPasswordBo bo) {
    SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
    String password = user.getPassword();
    if (!BCrypt.checkpw(bo.getOldPassword(), password)) {
      return R.fail("修改密码失败，旧密码错误");
    }
    if (BCrypt.checkpw(bo.getNewPassword(), password)) {
      return R.fail("新密码不能与旧密码相同");
    }
    double score = PasswordUtils.calculateStrength(bo.getNewPassword());
    if (score < 80) {
      throw new ServiceException("密码强度不够，必须满足80分以上");
    }

    if (userService.resetUserPwd(user.getUserId(), bo.getPasswordStrength(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
      return R.ok();
    }
    return R.fail("修改密码异常，请联系管理员");
  }

  /**
   * 重置其他用户密码
   *
   * @param bo 新旧密码
   */
  @Log(title = "个人信息", businessType = BusinessType.UPDATE)
  @PostMapping("/updatePwdOther")
  public R<Void> updatePwdOther(@Validated @RequestBody SysUserPasswordOtherBo bo) {
    SysUserVo user = userService.selectUserById(bo.getUserId());
    String password = user.getPassword();
    if (BCrypt.checkpw(bo.getNewPassword(), password)) {
      return R.fail("新密码不能与旧密码相同");
    }

    if (userService.resetUserPwd(user.getUserId(), bo.getPasswordStrength(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
      return R.ok();
    }
    return R.fail("修改密码异常，请联系管理员");
  }

  /**
   * 头像上传
   *
   * @param avatarfile 用户头像
   */
  @Log(title = "用户头像", businessType = BusinessType.UPDATE)
  @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public R<AvatarVo> avatar(@RequestPart("avatarfile") MultipartFile avatarfile) {
    if (!avatarfile.isEmpty()) {
      String extension = FileUtil.extName(avatarfile.getOriginalFilename());
      if (!StringUtils.equalsAnyIgnoreCase(extension, MimeTypeUtils.IMAGE_EXTENSION)) {
        return R.fail("文件格式不正确，请上传" + Arrays.toString(MimeTypeUtils.IMAGE_EXTENSION) + "格式");
      }
      SysOssVo oss = ossService.upload(avatarfile);
      String avatar = oss.getUrl();
      if (userService.updateUserAvatar(LoginHelper.getUserId(), oss.getOssId())) {
        AvatarVo avatarVo = new AvatarVo();
        avatarVo.setImgUrl(avatar);
        return R.ok(avatarVo);
      }
    }
    return R.fail("上传图片异常，请联系管理员");
  }
}

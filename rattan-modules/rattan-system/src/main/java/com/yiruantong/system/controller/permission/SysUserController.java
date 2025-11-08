package com.yiruantong.system.controller.permission;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.permission.bo.SysDeptBo;
import com.yiruantong.system.domain.permission.bo.SysPostBo;
import com.yiruantong.system.domain.permission.bo.SysRoleBo;
import com.yiruantong.system.domain.permission.bo.SysUserBo;
import com.yiruantong.system.domain.permission.vo.*;
import com.yiruantong.system.mapper.permission.SysUserMapper;
import com.yiruantong.system.service.tenant.ISysTenantService;
import com.yiruantong.system.domain.permission.vo.*;
import com.yiruantong.system.service.permission.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.domain.model.ModifyEmailBody;
import com.yiruantong.common.core.domain.model.ModifyPhoneNumberBody;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.system.domain.permission.vo.*;
import com.yiruantong.system.service.permission.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author YiRuanTong
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/user")
public class SysUserController extends AbstractController<SysUserMapper, SysUser, SysUserVo, SysUserBo> {

  private final ISysUserService userService;
  private final ISysRoleService roleService;
  private final ISysPostService postService;
  private final ISysDeptService deptService;
  private final ISysTenantService tenantService;
  private final ISysPermissionService sysPermissionService;

  /**
   * 获取用户列表
   */
//  @SaCheckPermission("system:user:list")
  @GetMapping("/list")
  public TableDataInfo<SysUserVo> list(SysUserBo user, PageQuery pageQuery) {
    return userService.selectPageUserList(user, pageQuery);
  }

  /**
   * 导出用户列表
   */
  @Log(title = "用户管理", businessType = BusinessType.EXPORT)
//  @SaCheckPermission("system:user:export")
  @PostMapping("/export")
  public void export(SysUserBo user, HttpServletResponse response) {
    List<SysUserVo> list = userService.selectUserList(user);
    List<SysUserExportVo> listVo = MapstructUtils.convert(list, SysUserExportVo.class);
    ExcelUtil.exportExcel(listVo, "用户数据", SysUserExportVo.class, response);
  }

  /**
   * 获取导入模板
   */
  @PostMapping("/importTemplate")
  public void importTemplate(HttpServletResponse response) {
    ExcelUtil.exportExcel(new ArrayList<>(), "用户数据", SysUserImportVo.class, response);
  }

  /**
   * 获取用户信息
   *
   * @return 用户信息
   */
  @GetMapping("/getInfo")
  public R<UserInfoVo> getInfo() {
    UserInfoVo userInfoVo = new UserInfoVo();
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
      // 超级管理员 如果重新加载用户信息需清除动态租户
      TenantHelper.clearDynamic();
    }
    SysUserVo user = userService.selectUserById(LoginHelper.getUserId());
    if (ObjectUtil.isNull(user)) {
      return R.fail("没有权限访问用户数据!");
    }
    user.setPackageId(LoginHelper.getPackageId());
    userInfoVo.setUser(user);
    userInfoVo.setPermissions(loginUser.getMenuPermission());
    userInfoVo.setRoles(loginUser.getRolePermission());
    return R.ok(userInfoVo);
  }

  /**
   * 根据用户编号获取详细信息
   *
   * @param userId 用户ID
   */
//  @SaCheckPermission("system:user:query")
  @GetMapping(value = {"/", "/{userId}"})
  public R<SysUserInfoVo> getInfo(@PathVariable(value = "userId", required = false) Long userId) {
    userService.checkUserDataScope(userId);
    SysUserInfoVo userInfoVo = new SysUserInfoVo();
    SysRoleBo roleBo = new SysRoleBo();
    roleBo.setEnable(UserConstants.ROLE_NORMAL);
    SysPostBo postBo = new SysPostBo();
    postBo.setStatus(UserConstants.POST_NORMAL);
    List<SysRoleVo> roles = roleService.selectRoleList(roleBo);
    userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
    userInfoVo.setPosts(postService.selectPostList(postBo));
    if (ObjectUtil.isNotNull(userId)) {
      SysUserVo sysUser = userService.selectUserById(userId);
      userInfoVo.setUser(sysUser);
      userInfoVo.setRoleIds(StreamUtils.toList(sysUser.getRoles(), SysRoleVo::getRoleId));
      userInfoVo.setPostIds(postService.selectPostListByUserId(userId));
    }
    return R.ok(userInfoVo);
  }

  /**
   * 新增用户
   */
  // @SaCheckPermission("system:user:add")
  @Log(title = "用户管理", businessType = BusinessType.INSERT)
  @PostMapping("/add")
  public R<Void> add(@Validated @RequestBody SysUserBo user) {
    if (!userService.checkUserNameUnique(user)) {
      return R.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
    } else if (StringUtils.isNotEmpty(user.getPhoneNumber()) && !userService.checkPhoneUnique(user)) {
      return R.fail("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
    } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
      return R.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
    }

    user.setPassword(BCrypt.hashpw(user.getPassword()));
    return toAjax(userService.insertUser(user));
  }

  /**
   * 修改用户
   */
  @SaCheckPermission("system:user:edit")
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editData(@Validated @RequestBody SysUserBo user) {
    userService.checkUserAllowed(user.getUserId());
    userService.checkUserDataScope(user.getUserId());
    deptService.checkDeptDataScope(user.getDeptId());
    if (!userService.checkUserNameUnique(user)) {
      return R.fail("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
    } else if (StringUtils.isNotEmpty(user.getPhoneNumber()) && !userService.checkPhoneUnique(user)) {
      return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
    } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
      return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
    }
    return toAjax(userService.updateUser(user));
  }

  /**
   * 删除用户
   *
   * @param userIds 角色ID串
   */
//  @SaCheckPermission("system:user:remove")
  @Log(title = "用户管理", businessType = BusinessType.DELETE)
  @DeleteMapping("remove/{userIds}")
  @Override
  public R<Void> remove(@PathVariable Long[] userIds) {
    if (ArrayUtil.contains(userIds, LoginHelper.getUserId())) {
      return R.fail("当前用户不能删除");
    }
    return toAjax(userService.deleteUserByIds(userIds));
  }

  /**
   * 重置密码
   */
  @SaCheckPermission("system:user:resetPwd")
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @PutMapping("/resetPwd")
  public R<Void> resetPwd(@RequestBody SysUserBo user) {
    userService.checkUserAllowed(user.getUserId());
    userService.checkUserDataScope(user.getUserId());
    user.setPassword(BCrypt.hashpw(user.getPassword()));
    return toAjax(userService.resetUserPwd(user.getUserId(), 0, user.getPassword()));
  }

  /**
   * 状态修改
   */
  @SaCheckPermission("system:user:edit")
  @Log(title = "用户管理", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public R<Void> changeStatus(@RequestBody SysUserBo user) {
    userService.checkUserAllowed(user.getUserId());
    userService.checkUserDataScope(user.getUserId());
    return toAjax(userService.updateUserStatus(user.getUserId(), user.getEnable()));
  }

  /**
   * 根据用户编号获取授权角色
   *
   * @param userId 用户ID
   */
  @SaCheckPermission("system:user:query")
  @GetMapping("/authRole/{userId}")
  public R<SysUserInfoVo> authRole(@PathVariable Long userId) {
    SysUserVo user = userService.selectUserById(userId);
    List<SysRoleVo> roles = roleService.selectRolesByUserId(userId);
    SysUserInfoVo userInfoVo = new SysUserInfoVo();
    userInfoVo.setUser(user);
    userInfoVo.setRoles(LoginHelper.isSuperAdmin(userId) ? roles : StreamUtils.filter(roles, r -> !r.isSuperAdmin()));
    return R.ok(userInfoVo);
  }

  /**
   * 用户授权角色
   *
   * @param userId  用户Id
   * @param roleIds 角色ID串
   */
  @SaCheckPermission("system:user:edit")
  @Log(title = "用户管理", businessType = BusinessType.GRANT)
  @PutMapping("/authRole")
  public R<Void> insertAuthRole(Long userId, Long[] roleIds) {
    userService.checkUserDataScope(userId);
    userService.insertUserAuth(userId, roleIds);
    return R.ok();
  }

  /**
   * 获取部门树列表
   */
//  @SaCheckPermission("system:user:list")
  @SaCheckLogin
  @GetMapping("/deptTree")
  public R<List<Tree<Long>>> deptTree(SysDeptBo dept) {
    return R.ok(deptService.selectDeptTreeList(dept));
  }

  /**
   * 获取部门下的所有用户信息
   */
  @SaCheckPermission("system:user:list")
  @GetMapping("/list/dept/{deptId}")
  public R<List<SysUserVo>> listByDept(@PathVariable @NotNull Long deptId) {
    return R.ok(userService.selectUserListByDept(deptId));
  }

  /**
   * 查询用户列表，用于用户选择场景
   */
  @SaCheckLogin
  @GetMapping("/selectUser")
  public TableDataInfo<SysUserVo> selectUser(SysUserBo user, PageQuery pageQuery) {
    return userService.selectPageUserList(user, pageQuery);
  }

  //#region 根据条件查询用户列表
  @PostMapping(value = "/getUserList")
  public R<List<Map<String, Object>>> getUserList(@RequestBody Map<String, Object> map) {
    return R.ok(userService.getUserList(map));
  }
  //#endregion

  /**
   * 修改用户手机号
   */
  @PostMapping("/modifyPhoneNumber")
  @Log(title = "修改用户手机号", businessType = BusinessType.MODIFY_USER_PHONE_NUMBER)
  public R<Void> modifyPhoneNumber(@Validated @RequestBody ModifyPhoneNumberBody user) {
    return userService.modifyPhoneNumber(user);
  }

  /**
   * 修改用户手机号
   */
  @PostMapping("/modifyEmail")
  @Log(title = "修改用户email", businessType = BusinessType.MODIFY_USER_EMAIL)
  public R<Void> modifyEmail(@Validated @RequestBody ModifyEmailBody emailBody) {
    return userService.modifyEmail(emailBody);
  }

  /**
   * 邮箱激活连接
   */
  @PostMapping("/sendActiveEmail")
  public R<Void> sendActiveEmail() {
    return sysPermissionService.sendActiveEmail();
  }

  /**
   * 邮箱激活连接
   */
  @PostMapping("/sendPhoneActivate")
  public R<Void> sendPhoneActivate(@Validated @RequestBody ModifyPhoneNumberBody phoneNumberBody) {
    return sysPermissionService.sendPhoneActivate(phoneNumberBody);
  }

  /**
   * 注册用户
   */
  @SaIgnore // 忽略接口鉴权
  @PostMapping("/register")
  public R<Void> register(@Validated @RequestBody SysUserBo userBo) {
    return userService.register(userBo);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/apiAdd")
  public R<Map<String, Object>> apiAdd(@Validated(AddGroup.class) @RequestBody SysUserBo bo) {
    return userService.apiAdd(bo);
  }

}

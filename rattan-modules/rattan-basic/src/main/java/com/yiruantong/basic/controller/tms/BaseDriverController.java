package com.yiruantong.basic.controller.tms;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.tms.BaseDriver;
import com.yiruantong.basic.domain.tms.bo.BaseDriverBo;
import com.yiruantong.basic.domain.tms.bo.BaseDriverPasswordBo;
import com.yiruantong.basic.domain.tms.vo.BaseDriverVo;
import com.yiruantong.basic.mapper.tms.BaseDriverMapper;
import com.yiruantong.basic.service.tms.IBaseDriverService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import com.yiruantong.system.domain.permission.vo.UserInfoVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 司机管理
 *
 * @author YRT
 * @date 2023-11-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/tms/driver")
public class BaseDriverController extends AbstractController<BaseDriverMapper, BaseDriver, BaseDriverVo, BaseDriverBo> {
  private final IBaseDriverService baseDriverService;

  /**
   * 获取司机下拉框信息
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getDriverNameList")
  public R<List<Map<String, Object>>> getDriverNameList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseDriverService.getDriverNameList(map);
    return R.ok(list);
  }

  /**
   * 查询司机
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> list = baseDriverService.getList(map);
    return R.ok(list);
  }

  /**
   * 重置其他用户密码
   *
   * @param bo 新旧密码
   */
  @Log(title = "修改货主信息", businessType = BusinessType.UPDATE)
  @PostMapping("/updatePwd")
  public R<Void> updatePwd(@Validated @RequestBody BaseDriverPasswordBo bo) {
    BaseDriver baseDriver = baseDriverService.getById(bo.getDriverId());
    String password = baseDriver.getUserPwd();
    if (BCrypt.checkpw(bo.getNewPassword(), password)) {
      return R.fail("新密码不能与旧密码相同");
    }

    if (baseDriverService.resetUserPwd(baseDriver.getDriverId(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
      return R.ok();
    }
    return R.fail("修改密码异常，请联系管理员");
  }

  /**
   * 获取登录货主信息
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
    BaseDriver consignor = baseDriverService.getById(loginUser.getUserId());
    if (ObjectUtil.isNull(consignor)) {
      return R.fail("没有权限访问用户数据!");
    }
    SysUserVo user = new SysUserVo();
    user.setUserId(consignor.getDriverId());
    user.setEnable(consignor.getEnable());
    user.setIsAdministrator((byte) 1);
    user.setNickName(consignor.getDriverName());
    user.setUserName(consignor.getDriverCode());
    user.setLoginIp(consignor.getLoginIp());
    user.setRoleName("司机");

    userInfoVo.setUser(user);
    userInfoVo.setPermissions(loginUser.getMenuPermission());
    userInfoVo.setRoles(loginUser.getRolePermission());
    return R.ok(userInfoVo);
  }
}

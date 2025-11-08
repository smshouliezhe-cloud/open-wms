package com.yiruantong.basic.controller.base;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.base.BaseConsignor;
import com.yiruantong.basic.domain.base.BaseConsignorCheckInRecord;
import com.yiruantong.basic.domain.base.bo.BaseConsignorAddBo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorBo;
import com.yiruantong.basic.domain.base.bo.BaseConsignorPasswordBo;
import com.yiruantong.basic.domain.base.bo.GetListBo;
import com.yiruantong.basic.domain.base.vo.BaseConsignorVo;
import com.yiruantong.basic.mapper.base.BaseConsignorMapper;
import com.yiruantong.basic.service.base.IBaseConsignorService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
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
 * 货主信息
 *
 * @author YRT
 * @date 2023-08-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/basic/base/consignor")
public class BaseConsignorController extends AbstractController<BaseConsignorMapper, BaseConsignor, BaseConsignorVo, BaseConsignorBo> {
  private final IBaseConsignorService baseConsignorService;

  /**
   * 查询货位
   *
   * @param getListBo 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getList")
  public R<List<Map<String, Object>>> getList(@RequestBody GetListBo getListBo) {
    List<Map<String, Object>> list = baseConsignorService.getList(getListBo);
    return R.ok(list);
  }

  /**
   * 重置其他用户密码
   *
   * @param bo 新旧密码
   */
  @Log(title = "修改货主信息", businessType = BusinessType.UPDATE)
  @PostMapping("/updatePwd")
  public R<Void> updatePwd(@Validated @RequestBody BaseConsignorPasswordBo bo) {
    BaseConsignor consignor = baseConsignorService.getById(bo.getConsignorId());
    String password = consignor.getPassword();
    if (BCrypt.checkpw(bo.getNewPassword(), password)) {
      return R.fail("新密码不能与旧密码相同");
    }

    if (baseConsignorService.resetUserPwd(consignor.getConsignorId(), BCrypt.hashpw(bo.getNewPassword())) > 0) {
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
    BaseConsignor consignor = baseConsignorService.getById(loginUser.getUserId());
    if (ObjectUtil.isNull(consignor)) {
      return R.fail("没有权限访问用户数据!");
    }
    SysUserVo user = new SysUserVo();
    user.setUserId(consignor.getConsignorId());
    user.setEnable(consignor.getEnable());
    user.setIsAdministrator((byte) 1);
    user.setNickName(consignor.getConsignorName());
    user.setUserName(consignor.getConsignorCode());
    user.setLoginIp(consignor.getLoginIp());
    user.setRoleName("货主");

    userInfoVo.setUser(user);
    userInfoVo.setPermissions(loginUser.getMenuPermission());
    userInfoVo.setRoles(loginUser.getRolePermission());
    return R.ok(userInfoVo);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody BaseConsignorAddBo bo) {
    return baseConsignorService.add(bo);
  }

  /**
   * 修改数据
   */
  @Log(title = "修改数据", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping("/updateInfo")
  public R<Map<String, Object>> updateInfo(@Validated(AddGroup.class) @RequestBody BaseConsignorBo bo) {
    return baseConsignorService.updateInfo(bo);
  }

  /**
   * 获取门店信息和明细信息
   *
   * @param queryBoList 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getConsignorData")
  public R<Map<String, Object>> getConsignorData(@RequestBody List<QueryBo> queryBoList) {
    return baseConsignorService.getConsignorData(queryBoList);
  }

  /**
   * 添加门店打卡记录
   *
   * @param bo 修改的数据
   * @return 返回保存结果
   */
  @PostMapping("/addCheckInRecord")
  public R<Map<String, Object>> addCheckInRecord(@RequestBody BaseConsignorCheckInRecord bo) {
    return baseConsignorService.addCheckInRecord(bo);
  }

  /**
   * 获取门店打开记录
   *
   * @param consignorName 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getCheckInRecord")
  public R<Map<String, Object>> getCheckInRecord(@RequestBody String consignorName) {
    return baseConsignorService.getCheckInRecord(consignorName);
  }

}

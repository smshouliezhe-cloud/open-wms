package com.yiruantong.system.controller.permission;

import com.yiruantong.system.domain.permission.SysRoleAuth;
import com.yiruantong.system.domain.permission.bo.SysRoleAuthBo;
import com.yiruantong.system.domain.permission.vo.SysRoleAuthVo;
import com.yiruantong.system.mapper.permission.SysRoleAuthMapper;
import com.yiruantong.system.service.permission.ISysRoleAuthService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 功能权限
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/roleAuth")
public class SysRoleAuthController extends AbstractController<SysRoleAuthMapper, SysRoleAuth, SysRoleAuthVo, SysRoleAuthBo> {
  private final ISysRoleAuthService sysRoleAuthService;

  /**
   * 保存模块功能权限
   */
  @PostMapping("/saveAuthMenu")
  public R<Void> saveAuthMenu(@RequestBody Map<String, Object> map) {
    return sysRoleAuthService.saveAuthMenu(map);
  }

  /**
   * 获取用户数据权限
   */
  @PostMapping("/getDataAuth")
  public R<List<Map<String, Object>>> getDataAuth(@RequestBody Map<String, Object> map) {
    return sysRoleAuthService.getDataAuth(map);
  }

  /**
   * 保存用户数据权限
   */
  @PostMapping("/saveUserDataAuth")
  public R<List<Map<String, Object>>> saveUserDataAuth(@RequestBody Map<String, Object> map) {
    return sysRoleAuthService.saveUserDataAuth(map);
  }
}

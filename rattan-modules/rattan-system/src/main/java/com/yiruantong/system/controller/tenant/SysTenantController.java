package com.yiruantong.system.controller.tenant;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.convert.Convert;
import com.yiruantong.system.domain.tenant.SysTenant;
import com.yiruantong.system.domain.tenant.bo.SysTenantBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantVo;
import com.yiruantong.system.mapper.tenant.SysTenantMapper;
import com.yiruantong.system.service.tenant.ISysTenantService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 租户管理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant/tenant")
public class SysTenantController extends AbstractController<SysTenantMapper, SysTenant, SysTenantVo, SysTenantBo> {

  private final ISysTenantService tenantService;

  /**
   * 获取租户详细信息
   *
   * @param id 主键
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenant:query")
  @GetMapping("/{id}")
  public R<SysTenantVo> getInfo(@NotNull(message = "主键不能为空")
                                @PathVariable Long id) {
    return R.ok(tenantService.queryById(id));
  }

  /**
   * 获取租户详细信息
   *
   * @param tenantId 账套ID
   */
  @PostMapping("/getTenantInfo/{tenantId}")
  public R<SysTenantVo> getTenantInfo(@NotNull(message = "主键不能为空") @PathVariable String tenantId) {
    return R.ok(tenantService.queryByTenantId(tenantId));
  }

  /**
   * 状态修改
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenant:edit")
  @Log(title = "租户", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public R<Void> changeStatus(@RequestBody SysTenantBo bo) {
    tenantService.checkTenantAllowed(bo.getTenantId());
    return toAjax(tenantService.updateTenantStatus(bo));
  }

  /**
   * 动态切换租户
   *
   * @param tenantId 租户ID
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @GetMapping("/dynamic/{tenantId}")
  public R<Void> dynamicTenant(@NotBlank(message = "租户ID不能为空") @PathVariable String tenantId) {
    TenantHelper.setDynamic(tenantId);
    return R.ok();
  }

  /**
   * 清除动态租户
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @GetMapping("/dynamic/clear")
  public R<Void> dynamicClear() {
    TenantHelper.clearDynamic();
    return R.ok();
  }


  /**
   * 同步租户套餐
   *
   * @param tenantId  租户id
   * @param packageId 套餐id
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenant:edit")
  @Log(title = "租户", businessType = BusinessType.UPDATE)
  @GetMapping("/syncTenantPackage")
  public R<Void> syncTenantPackage(@NotBlank(message = "租户ID不能为空") String tenantId,
                                   @NotNull(message = "套餐ID不能为空") Long packageId) {
    return toAjax(TenantHelper.ignore(() -> tenantService.syncTenantPackage(tenantId, packageId)));
  }

  /**
   * 根据手机号获取所有账套列表
   *
   * @param map 查询参数
   */
  @PostMapping("/getMyTenantList")
  public R<List<SysTenantVo>> getMyTenantList(@RequestBody Map<String, Object> map) {
    String mobile = Convert.toStr(map.get("mobile"));
    String password = Convert.toStr(map.get("password"));
    return R.ok(tenantService.getMyTenantList(mobile, password));
  }

  /**
   * 根据手机号获取所有账套列表
   *
   * @param tenantId 租户ID
   */
  @PostMapping("/cancelApp/{tenantId}")
  public R<Void> cancelApp(@NotBlank(message = "租户ID不能为空") @PathVariable String tenantId) {
    return tenantService.cancelApp(tenantId);
  }

  /**
   * 根据手机号获取所有账套列表
   *
   * @param map 参数
   */
  @PostMapping("/syncBasicData")
  public R<Void> syncBasicData(@RequestBody Map<String, Object> map) {
    String targetTenantId = Convert.toStr(map.get("targetTenantId"));
    List<Long> menuIdList = Convert.toList(Long.class, map.get("menuIdList"));
    return tenantService.syncBasicData(targetTenantId, menuIdList);
  }
}

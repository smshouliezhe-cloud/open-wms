package com.yiruantong.system.controller.tenant;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.hutool.core.convert.Convert;
import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.system.domain.tenant.bo.SysTenantPackageBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantPackageVo;
import com.yiruantong.system.mapper.tenant.SysTenantPackageMapper;
import com.yiruantong.system.service.tenant.ISysTenantPackageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.tenant.TenantAppCommandEnum;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 租户套餐管理
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/tenant/tenantPackage")
public class SysTenantPackageController extends AbstractController<SysTenantPackageMapper, SysTenantPackage, SysTenantPackageVo, SysTenantPackageBo> {

  private final ISysTenantPackageService tenantPackageService;

  /**
   * 查询租户套餐列表
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:list")
  @GetMapping("/list")
  public TableDataInfo<SysTenantPackageVo> list(SysTenantPackageBo bo, PageQuery pageQuery) {
    return tenantPackageService.queryPageList(bo, pageQuery);
  }

  /**
   * 查询租户套餐下拉选列表
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:list")
  @GetMapping("/selectList")
  public R<List<SysTenantPackageVo>> selectList() {
    return R.ok(tenantPackageService.selectList());
  }

  /**
   * 导出租户套餐列表
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:export")
  @Log(title = "租户套餐", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(SysTenantPackageBo bo, HttpServletResponse response) {
    List<SysTenantPackageVo> list = tenantPackageService.queryList(bo);
    ExcelUtil.exportExcel(list, "租户套餐", SysTenantPackageVo.class, response);
  }

  /**
   * 获取租户套餐详细信息
   *
   * @param packageId 主键
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:query")
  @GetMapping("/{packageId}")
  public R<SysTenantPackageVo> getInfo(@NotNull(message = "主键不能为空")
                                       @PathVariable Long packageId) {
    return R.ok(tenantPackageService.queryById(packageId));
  }

  /**
   * 新增租户套餐
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:add")
  @Log(title = "租户套餐", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping()
  public R<Void> add(@Validated(AddGroup.class) @RequestBody SysTenantPackageBo bo) {
    return toAjax(tenantPackageService.insertByBo(bo));
  }

  /**
   * 修改租户套餐
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:edit")
  @Log(title = "租户套餐", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PutMapping()
  public R<Void> editData(@Validated(EditGroup.class) @RequestBody SysTenantPackageBo bo) {
    return toAjax(tenantPackageService.updateByBo(bo));
  }

  /**
   * 状态修改
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
  @SaCheckPermission("system:tenantPackage:edit")
  @Log(title = "租户套餐", businessType = BusinessType.UPDATE)
  @PutMapping("/changeStatus")
  public R<Void> changeStatus(@RequestBody SysTenantPackageBo bo) {
    return toAjax(tenantPackageService.updatePackageStatus(bo));
  }

  /**
   * 状态修改
   */
  @Log(title = "用户申请APP", businessType = BusinessType.OPEN)
  @PostMapping("/useApp")
  public R<Void> useApp(@RequestBody Map<String, Object> map) {
    Long packageId = Convert.toLong(map.get("packageId"));
    String command = Convert.toStr(map.get("command"));
    TenantAppCommandEnum commandEnum = TenantAppCommandEnum.matchingEnum(command);
    return tenantPackageService.useApp(packageId, commandEnum);
  }
}

package com.yiruantong.system.controller.core;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.hutool.core.lang.tree.Tree;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.core.bo.SysMenuBo;
import com.yiruantong.system.domain.core.vo.MenuTreeSelectVo;
import com.yiruantong.system.domain.core.vo.RouterVo;
import com.yiruantong.system.domain.core.vo.SysMenuBaseVo;
import com.yiruantong.system.domain.core.vo.SysMenuVo;
import com.yiruantong.system.mapper.core.SysMenuMapper;
import com.yiruantong.system.service.core.ISysMenuService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 菜单信息
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/menu")
public class SysMenuController extends AbstractController<SysMenuMapper, SysMenu, SysMenuVo, SysMenuBo> {
  private final ISysMenuService menuService;

  /**
   * 获取路由信息
   *
   * @return 路由信息
   */
  @PostMapping("/getRouters")
  public R<List<RouterVo>> getRouters() {
    List<SysMenu> menus = menuService.selectMenuTreeByUserId(LoginHelper.getUserId());
    List<RouterVo> routerVos = menuService.buildMenus(menus);
    return R.ok(routerVos);
  }

  /**
   * 获取菜单列表
   */
  @SaCheckRole(
    value = {TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY},
    mode = SaMode.OR)
//  @SaCheckPermission("system:MENU:list")
  @GetMapping("/list")
  public R<List<SysMenuVo>> list(SysMenuBo menu) {
    List<SysMenuVo> menus = menuService.selectMenuList(menu, LoginHelper.getUserId());
    return R.ok(menus);
  }

  /**
   * 根据菜单编号获取详细信息
   *
   * @param menuId 菜单ID
   */
  @SaCheckRole(
    value = {TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY},
    mode = SaMode.OR)
//  @SaCheckPermission("system:MENU:query")
  @GetMapping(value = "/{menuId}")
  public R<SysMenuVo> getInfo(@PathVariable Long menuId) {
    return R.ok(menuService.selectMenuById(menuId));
  }

  /**
   * 获取菜单下拉树列表
   */
  @SaCheckRole(
    value = {TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY},
    mode = SaMode.OR)
//  @SaCheckPermission("system:MENU:query")
  @GetMapping("/treeselect")
  public R<List<Tree<Long>>> treeselect(SysMenuBo menu) {
    List<SysMenuVo> menus = menuService.selectMenuList(menu, LoginHelper.getUserId());
    return R.ok(menuService.buildMenuTreeSelect(menus));
  }

  /**
   * 加载对应角色菜单列表树
   *
   * @param roleId 角色ID
   */
  @SaCheckRole(
    value = {TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY},
    mode = SaMode.OR)
//  @SaCheckPermission("system:MENU:query")
  @GetMapping(value = "/roleMenuTreeselect/{roleId}")
  public R<MenuTreeSelectVo> roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
    List<SysMenuVo> menus = menuService.selectMenuList(LoginHelper.getUserId());
    MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
    selectVo.setCheckedKeys(menuService.selectMenuListByRoleId(roleId));
    selectVo.setMenus(menuService.buildMenuTreeSelect(menus));
    return R.ok(selectVo);
  }

  /**
   * 加载对应租户套餐菜单列表树
   *
   * @param packageId 租户套餐ID
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
//  @SaCheckPermission("system:MENU:query")
  @GetMapping(value = "/tenantPackageMenuTreeselect/{packageId}")
  public R<MenuTreeSelectVo> tenantPackageMenuTreeSelect(@PathVariable("packageId") Long packageId) {
    List<SysMenuVo> menus = menuService.selectMenuList(LoginHelper.getUserId());
    MenuTreeSelectVo selectVo = new MenuTreeSelectVo();
    selectVo.setCheckedKeys(menuService.selectMenuListByPackageId(packageId));
    selectVo.setMenus(menuService.buildMenuTreeSelect(menus));
    return R.ok(selectVo);
  }

  /**
   * 新增菜单
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
//  @SaCheckPermission("system:MENU:add")
  @Log(title = "菜单管理", businessType = BusinessType.INSERT)
  @PostMapping
  public R<Void> add(@Validated @RequestBody SysMenuBo menu) {
    if (!menuService.checkMenuNameUnique(menu)) {
      return R.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
    } else if (menu.getIsFrame() == UserConstants.YES_FRAME && !StringUtils.ishttp(menu.getPath())) {
      return R.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
    }
    return toAjax(menuService.insertMenu(menu));
  }

  /**
   * 修改菜单
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
//  @SaCheckPermission("system:MENU:edit")
  @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editData(@Validated @RequestBody SysMenuBo menu) {
    if (!menuService.checkMenuNameUnique(menu)) {
      return R.fail("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
    } else if (UserConstants.YES_FRAME == menu.getIsFrame() && !StringUtils.ishttp(menu.getPath())) {
      return R.fail("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
    } else if (menu.getMenuId().equals(menu.getParentId())) {
      return R.fail("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
    }
    return toAjax(menuService.updateMenu(menu));
  }

  /**
   * 删除菜单
   *
   * @param menuId 菜单ID
   */
  @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
//  @SaCheckPermission("system:MENU:remove")
  @Log(title = "菜单管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{menuId}")
  public R<Void> remove(@PathVariable("menuId") Long menuId) {
    if (menuService.hasChildByMenuId(menuId)) {
      return R.warn("存在子菜单,不允许删除");
    }
    if (menuService.checkMenuExistRole(menuId)) {
      return R.warn("菜单已分配,不允许删除");
    }
    return toAjax(menuService.deleteMenuById(menuId));
  }

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return menuService.searchTree(filterText);
  }

  /**
   * 搜索菜单
   */
  @PostMapping("/getRootMenu")
  public R<List<SysMenuBaseVo>> getRootMenu() {
    return menuService.getRootMenu();
  }
}

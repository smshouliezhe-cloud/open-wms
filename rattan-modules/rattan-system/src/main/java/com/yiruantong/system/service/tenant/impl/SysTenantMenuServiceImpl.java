package com.yiruantong.system.service.tenant.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.core.vo.SysMenuVo;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.system.domain.tenant.bo.SysTenantMenuBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantMenuVo;
import com.yiruantong.system.mapper.tenant.SysTenantMenuMapper;
import com.yiruantong.system.service.core.ISysMenuService;
import com.yiruantong.system.service.tenant.ISysTenantMenuService;
import com.yiruantong.system.service.tenant.ISysTenantPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 租户套餐菜单Service业务层处理
 *
 * @author YRT
 * @date 2024-05-02
 */
@RequiredArgsConstructor
@Service
public class SysTenantMenuServiceImpl extends ServiceImplPlus<SysTenantMenuMapper, SysTenantMenu, SysTenantMenuVo, SysTenantMenuBo> implements ISysTenantMenuService {
  private final ISysTenantPackageService sysTenantPackageService;
  private final ISysMenuService sysMenuService;
  private final SysTenantMenuMapper sysTenantMenuMapper;

  //#region 递归查找菜单是否存在可用

  /**
   * 递归查找菜单是否存在可用
   *
   * @param sysMenuVoList 完整菜单集合
   * @param menuVo        当前菜单
   * @return 是否可用
   */
  private Boolean recursiveFunction(List<SysMenuVo> sysMenuVoList, SysMenuVo menuVo) {
    if (menuVo.getParentId() == 0L) {
      return true;
    }

    SysMenuVo parentMenuVo = sysMenuVoList.stream().filter(f -> NumberUtils.equals(menuVo.getParentId(), f.getMenuId())).findFirst().orElse(null);
    if (ObjectUtil.isNotNull(parentMenuVo)) {
      if (parentMenuVo.getParentId() == 0L) {
        return true;
      } else {
        return recursiveFunction(sysMenuVoList, parentMenuVo);
      }
    } else {
      return false;
    }
  }
  //#endregion

  //#region 重置为标准菜单

  /**
   * 重置为标准菜单
   *
   * @param map 查询条件
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void resetDefault(Map<String, Object> map) {
    Long packageId = Convert.toLong(map.get("packageId"));
    SysTenantPackage sysTenantPackage = sysTenantPackageService.getById(packageId);
    Assert.isFalse(sysTenantPackage == null, packageId + "套餐不存在");

    LambdaQueryWrapper<SysMenu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper
      .eq(SysMenu::getEnable, EnableEnum.ENABLE.getId())
      .isNotNull(SysMenu::getPath);
    List<SysMenuVo> sysMenuVoList = sysMenuService.selectList(menuLambdaQueryWrapper);

    List<SysMenuVo> enableMenuVoList = new ArrayList<>(); // 可用菜单
    for (var item : sysMenuVoList) {
      if (recursiveFunction(sysMenuVoList, item)) {
        enableMenuVoList.add(item);
      }
    }

    // 清空现有菜单
    LambdaQueryWrapper<SysTenantMenu> tenantMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tenantMenuLambdaQueryWrapper.eq(SysTenantMenu::getPackageId, packageId);
    this.remove(tenantMenuLambdaQueryWrapper);

    // 复制标准菜单
    List<SysTenantMenu> sysTenantMenuList = BeanUtil.copyToList(enableMenuVoList, SysTenantMenu.class);
    sysTenantMenuList.forEach(item -> {
      item.setPackageId(sysTenantPackage.getPackageId());
      item.setPackageName(sysTenantPackage.getPackageName());
    });
    this.saveBatch(sysTenantMenuList);
  }
  //#endregion

  //#region selectMenuByTenantId
  @Override
  public List<SysMenu> selectMenuByTenantId(String tenantId) {
    LambdaQueryWrapper<SysTenantMenu> tenantMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tenantMenuLambdaQueryWrapper.eq(SysTenantMenu::getPackageId, LoginHelper.getPackageId())
      .eq(SysTenantMenu::getEnable, EnableEnum.ENABLE.getId())
      .and(a -> a.isNotNull(SysTenantMenu::getPath).or().isNotNull(SysTenantMenu::getIsFrame))
      .orderByDesc(SysTenantMenu::getOrderNum)
      .orderByAsc(SysTenantMenu::getTentantMenuId);
    List<SysTenantMenu> sysTenantMenuList = this.list(tenantMenuLambdaQueryWrapper);

    return BeanUtil.copyToList(sysTenantMenuList, SysMenu.class);
  }
  //#endregion

  //#region getMenuByMenuId 根据菜单ID获取租户菜单
  @Override
  public SysMenu getMenuByMenuId(MenuEnum menuEnum) {
    LambdaQueryWrapper<SysTenantMenu> tenantMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tenantMenuLambdaQueryWrapper.eq(SysTenantMenu::getPackageId, LoginHelper.getPackageId())
      .eq(SysTenantMenu::getMenuId, menuEnum.getId());
    SysTenantMenu sysTenantMenu = this.getOnly(tenantMenuLambdaQueryWrapper);

    return BeanUtil.toBean(sysTenantMenu, SysMenu.class);
  }
  //#endregion

  //#region selectMenuByUserId
  @Override
  public List<SysMenu> selectMenuByUserId(Long packageId, Long userId) {
    return sysTenantMenuMapper.selectMenuByUserId(packageId, userId);
  }
  //#endregion

  //#region addMenu
  @Transactional(rollbackFor = Exception.class)
  @Override
  public R<Void> addMenu(Long packageId, List<Long> menuIdList) {
    SysTenantPackage sysTenantPackage = sysTenantPackageService.getById(packageId);
    LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    sysMenuLambdaQueryWrapper.in(SysMenu::getMenuId, menuIdList);
    List<SysMenu> sysMenuList = sysMenuService.list(sysMenuLambdaQueryWrapper);
    List<SysTenantMenu> tenantMenuList = sysMenuList.stream().map(m -> {
      SysTenantMenu sysTenantMenu = BeanUtil.copyProperties(m, SysTenantMenu.class);
      sysTenantMenu.setPackageId(packageId);
      sysTenantMenu.setPackageName(sysTenantPackage.getPackageName());

      return sysTenantMenu;
    }).toList();
    this.saveBatch(tenantMenuList);
    return R.ok();
  }
  //#endregion

  //#region selectMenuById
  @Override
  public SysTenantMenu selectMenuById(Long menuId) {
    LambdaQueryWrapper<SysTenantMenu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper
      .eq(SysTenantMenu::getMenuId, menuId)
      .eq(SysTenantMenu::getPackageId, LoginHelper.getPackageId());

    return this.getOnly(menuLambdaQueryWrapper);
  }
  //#endregion
  //#region copyEditor 复制编辑页面数据

  /**
   * 复制编辑页面数据
   *
   * @param saveEditorBo 复制信息bo
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysTenantMenuBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysTenantMenu sysTenantMenu = this.getById(saveEditorBo.getIdValue());

    // 保存主表
    sysTenantMenu.setTentantMenuId(null);
    this.save(sysTenantMenu);

    return R.ok("复制成功");
  }
  //#endregion
}

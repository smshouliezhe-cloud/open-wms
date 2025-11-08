package com.yiruantong.system.service.tenant;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.yiruantong.system.domain.tenant.bo.SysTenantMenuBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantMenuVo;

import java.util.List;
import java.util.Map;

/**
 * 租户套餐菜单Service接口
 *
 * @author YRT
 * @date 2024-05-02
 */
public interface ISysTenantMenuService extends IServicePlus<SysTenantMenu, SysTenantMenuVo, SysTenantMenuBo> {
  /**
   * 重置为标准菜单
   *
   * @param map 查询条件
   */
  void resetDefault(Map<String, Object> map);

  /**
   * 根据租户ID获取菜单列表
   *
   * @param tenantId 租户ID
   * @return
   */
  List<SysMenu> selectMenuByTenantId(String tenantId);

  /**
   * 根据菜单ID获取租户菜单
   *
   * @param menuEnum 菜单ID
   * @return
   */
  SysMenu getMenuByMenuId(MenuEnum menuEnum);

  /**
   * 根据租户ID和用户ID获取菜单列表
   *
   * @param packageId 套餐ID
   * @param userId    用户ID
   * @return
   */
  List<SysMenu> selectMenuByUserId(Long packageId, Long userId);

  /**
   * 添加菜单
   *
   * @param packageId  套餐ID
   * @param menuIdList 菜单列表
   * @return
   */
  R<Void> addMenu(Long packageId, List<Long> menuIdList);

  /**
   * 根据菜单ID查询信息
   *
   * @param menuId 菜单ID
   * @return 菜单信息
   */
  SysTenantMenu selectMenuById(Long menuId);
}

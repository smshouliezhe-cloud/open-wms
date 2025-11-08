package com.yiruantong.system.service.core;

import cn.hutool.core.lang.tree.Tree;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.core.bo.SysMenuBo;
import com.yiruantong.system.domain.core.vo.RouterVo;
import com.yiruantong.system.domain.core.vo.SysMenuBaseVo;
import com.yiruantong.system.domain.core.vo.SysMenuVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 菜单 业务层
 *
 * @author YiRuanTong
 */
public interface ISysMenuService extends IServicePlus<SysMenu, SysMenuVo, SysMenuBo> {

  /**
   * 根据用户查询系统菜单列表
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenuVo> selectMenuList(Long userId);

  /**
   * 根据用户查询系统菜单列表
   *
   * @param menu   菜单信息
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId);

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  Set<String> selectMenuPermsByUserId(Long userId);

  /**
   * 根据角色ID查询权限
   *
   * @param roleId 角色ID
   * @return 权限列表
   */
  Set<String> selectMenuPermsByRoleId(Long roleId);

  /**
   * 根据用户ID查询菜单树信息
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  List<SysMenu> selectMenuTreeByUserId(Long userId);

  /**
   * 根据菜单ID查询菜单
   *
   * @param menuEnum 菜单ID
   * @return 菜单列表
   */
  SysMenu getMenuById(MenuEnum menuEnum);

  /**
   * 根据角色ID查询菜单树信息
   *
   * @param roleId 角色ID
   * @return 选中菜单列表
   */
  List<Long> selectMenuListByRoleId(Long roleId);

  /**
   * 根据租户套餐ID查询菜单树信息
   *
   * @param packageId 租户套餐ID
   * @return 选中菜单列表
   */
  List<Long> selectMenuListByPackageId(Long packageId);

  /**
   * 构建前端路由所需要的菜单
   *
   * @param menus 菜单列表
   * @return 路由列表
   */
  List<RouterVo> buildMenus(List<SysMenu> menus);

  /**
   * 构建前端所需要下拉树结构
   *
   * @param menus 菜单列表
   * @return 下拉树结构列表
   */
  List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus);

  /**
   * 根据菜单ID查询信息
   *
   * @param menuId 菜单ID
   * @return 菜单信息
   */
  SysMenuVo selectMenuById(Number menuId);

  /**
   * 是否存在菜单子节点
   *
   * @param menuId 菜单ID
   * @return 结果 true 存在 false 不存在
   */
  boolean hasChildByMenuId(Long menuId);

  /**
   * 查询菜单是否存在角色
   *
   * @param menuId 菜单ID
   * @return 结果 true 存在 false 不存在
   */
  boolean checkMenuExistRole(Long menuId);

  /**
   * 新增保存菜单信息
   *
   * @param bo 菜单信息
   * @return 结果
   */
  int insertMenu(SysMenuBo bo);

  /**
   * 修改保存菜单信息
   *
   * @param bo 菜单信息
   * @return 结果
   */
  int updateMenu(SysMenuBo bo);

  /**
   * 删除菜单管理信息
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  int deleteMenuById(Long menuId);

  /**
   * 校验菜单名称是否唯一
   *
   * @param menu 菜单信息
   * @return 结果
   */
  boolean checkMenuNameUnique(SysMenuBo menu);

  /**
   * 搜索菜单
   *
   * @param filterText 查询参数
   * @return
   */
  R<List<Map<String, Object>>> searchTree(String filterText);

  /**
   * 获取根主菜单
   *
   * @return R
   */
  R<List<SysMenuBaseVo>> getRootMenu();
}

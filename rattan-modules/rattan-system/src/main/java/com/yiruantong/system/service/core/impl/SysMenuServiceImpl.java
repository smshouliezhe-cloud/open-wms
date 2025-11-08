package com.yiruantong.system.service.core.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.core.SysMenu;
import com.yiruantong.system.domain.core.bo.SysMenuBo;
import com.yiruantong.system.domain.core.vo.MetaVo;
import com.yiruantong.system.domain.core.vo.RouterVo;
import com.yiruantong.system.domain.core.vo.SysMenuBaseVo;
import com.yiruantong.system.domain.core.vo.SysMenuVo;
import com.yiruantong.system.domain.permission.SysRole;
import com.yiruantong.system.domain.permission.SysRoleMenu;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.system.mapper.core.SysMenuMapper;
import com.yiruantong.system.mapper.permission.SysRoleMapper;
import com.yiruantong.system.mapper.permission.SysRoleMenuMapper;
import com.yiruantong.system.mapper.tenant.SysTenantPackageMapper;
import com.yiruantong.system.service.core.ISysMenuService;
import com.yiruantong.system.service.tenant.ISysTenantMenuService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

/**
 * 菜单 业务层处理
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl extends ServiceImplPlus<SysMenuMapper, SysMenu, SysMenuVo, SysMenuBo> implements ISysMenuService {

  private final SysMenuMapper baseMapper;
  private final SysRoleMapper roleMapper;
  private final SysRoleMenuMapper roleMenuMapper;
  private final SysTenantPackageMapper tenantPackageMapper;

  /**
   * 根据用户查询系统菜单列表
   *
   * @param userId 用户ID
   * @return 菜单列表
   */
  @Override
  public List<SysMenuVo> selectMenuList(Long userId) {
    return selectMenuList(new SysMenuBo(), userId);
  }

  /**
   * 查询系统菜单列表
   *
   * @param menu 菜单信息
   * @return 菜单列表
   */
  @Override
  public List<SysMenuVo> selectMenuList(SysMenuBo menu, Long userId) {
    List<SysMenuVo> menuList;
    // 管理员显示所有菜单信息
    if (LoginHelper.isSuperAdmin(userId)) {
      menuList = baseMapper.selectVoList(new LambdaQueryWrapper<SysMenu>().like(StringUtils.isNotBlank(menu.getMenuName()), SysMenu::getMenuName, menu.getMenuName()).eq(ObjectUtil.isNotEmpty(menu.getVisible()), SysMenu::getVisible, menu.getVisible()).eq(ObjectUtil.isNotEmpty(menu.getEnable()), SysMenu::getEnable, menu.getEnable()).orderByAsc(SysMenu::getParentId).orderByAsc(SysMenu::getOrderNum));
    } else {
      QueryWrapper<SysMenu> wrapper = Wrappers.query();
      wrapper.eq("sur.user_id", userId).like(StringUtils.isNotBlank(menu.getMenuName()), "m.menu_name", menu.getMenuName()).eq(ObjectUtil.isNotEmpty(menu.getVisible()), "m.visible", menu.getVisible()).eq(ObjectUtil.isNotEmpty(menu.getEnable()), "m.status", menu.getEnable()).orderByAsc("m.parent_id").orderByAsc("m.order_num");
      List<SysMenu> list = baseMapper.selectMenuListByUserId(wrapper);
      menuList = MapstructUtils.convert(list, SysMenuVo.class);
    }
    return menuList;
  }

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  @Override
  public Set<String> selectMenuPermsByUserId(Long userId) {
    List<String> perms = baseMapper.selectMenuPermsByUserId(userId);
    Set<String> permsSet = new HashSet<>();
    for (String perm : perms) {
      if (StringUtils.isNotEmpty(perm)) {
        permsSet.addAll(StringUtils.splitList(perm.trim()));
      }
    }
    return permsSet;
  }

  /**
   * 根据角色ID查询权限
   *
   * @param roleId 角色ID
   * @return 权限列表
   */
  @Override
  public Set<String> selectMenuPermsByRoleId(Long roleId) {
    List<String> perms = baseMapper.selectMenuPermsByRoleId(roleId);
    Set<String> permsSet = new HashSet<>();
    for (String perm : perms) {
      if (StringUtils.isNotEmpty(perm)) {
        permsSet.addAll(StringUtils.splitList(perm.trim()));
      }
    }
    return permsSet;
  }

  /**
   * 根据用户ID查询菜单
   *
   * @param userId 用户名称
   * @return 菜单列表
   */
  @Override
  public List<SysMenu> selectMenuTreeByUserId(Long userId) {
    // 验证API用户不允许登录系统
    LoginHelper.checkConsoleUser();

    List<SysMenu> menus;
    // 000000=开发环境账套
    if (StringUtils.equals(TenantHelper.getTenantId(), TenantConstants.DEFAULT_TENANT_ID)) {
      if (LoginHelper.isSuperAdmin(userId)) {
        menus = baseMapper.selectMenuTreeAll();
      } else {
        menus = baseMapper.selectMenuTreeByUserId(userId);
      }
    } else {
      ISysTenantMenuService sysTenantMenuService = SpringUtils.getBean(ISysTenantMenuService.class);
      // 常规账套用户
      if (LoginHelper.isSuperAdmin(userId)) {
        menus = sysTenantMenuService.selectMenuByTenantId(TenantHelper.getTenantId());
      } else {
        menus = sysTenantMenuService.selectMenuByUserId(LoginHelper.getPackageId(), userId);
      }
    }
    menus.forEach(x -> {
      if (StringUtils.isEmpty(x.getPath())) {
        x.setPath("iframe-path-" + x.getMenuId());
      }
    });
    return getChildPerms(menus, 0);
  }

  /**
   * 根据菜单ID查询菜单
   *
   * @param menuEnum 菜单ID
   * @return 菜单列表
   */
  @Override
  public SysMenu getMenuById(MenuEnum menuEnum) {
    // 验证API用户不允许登录系统
    LoginHelper.checkConsoleUser();

    SysMenu sysMenu;
    // 000000=开发环境账套
    if (StringUtils.equals(TenantHelper.getTenantId(), TenantConstants.DEFAULT_TENANT_ID)) {
      sysMenu = this.getById(menuEnum.getId());
    } else {
      ISysTenantMenuService sysTenantMenuService = SpringUtils.getBean(ISysTenantMenuService.class);
      // 常规账套用户
      sysMenu = sysTenantMenuService.getMenuByMenuId(menuEnum);
    }

    return sysMenu;
  }

  /**
   * 根据角色ID查询菜单树信息
   *
   * @param roleId 角色ID
   * @return 选中菜单列表
   */
  @Override
  public List<Long> selectMenuListByRoleId(Long roleId) {
    SysRole role = roleMapper.selectById(roleId);
    return baseMapper.selectMenuListByRoleId(roleId, role.getMenuCheckStrictly());
  }

  /**
   * 根据租户套餐ID查询菜单树信息
   *
   * @param packageId 租户套餐ID
   * @return 选中菜单列表
   */
  @Override
  public List<Long> selectMenuListByPackageId(Long packageId) {
    SysTenantPackage tenantPackage = tenantPackageMapper.selectById(packageId);
    List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);
    if (CollUtil.isEmpty(menuIds)) {
      return List.of();
    }
    List<Long> parentIds = null;
    if (Objects.equals(tenantPackage.getMenuCheckStrictly(), EnableEnum.ENABLE.getId())) {
      LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<SysMenu>().select(SysMenu::getParentId).in(SysMenu::getMenuId, menuIds);
      parentIds = this.list(sysMenuLambdaQueryWrapper).stream().map(SysMenu::getMenuId).toList();
    }
    LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<SysMenu>().in(SysMenu::getMenuId, menuIds).notIn(CollUtil.isNotEmpty(parentIds), SysMenu::getMenuId, parentIds);
    return this.list(sysMenuLambdaQueryWrapper).stream().map(SysMenu::getMenuId).toList();
  }

  /**
   * 构建前端路由所需要的菜单
   * 路由name命名规则 path首字母转大写 + id
   *
   * @param menus 菜单列表
   * @return 路由列表
   */
  @Override
  public List<RouterVo> buildMenus(List<SysMenu> menus) {
    List<RouterVo> routers = new LinkedList<>();
    for (SysMenu menu : menus) {
      if (StringUtils.isNotEmpty(menu.getIsLink())) {

        HashMap<String, Object> msgData = new HashMap<>();

        msgData.put("userid", LoginHelper.getUserId().toString());
        var jsonMsgData = JsonUtils.toJsonString(msgData);
        String encodedString = URLEncoder.encode(jsonMsgData, StandardCharsets.UTF_8);
        menu.setIsLink(StringUtils.replace(menu.getIsLink(), "{userid}", encodedString));
        // MENU.setIsLink(StringUtils.replace(MENU.getIsLink(), "{roleid}", LoginHelper.getLoginUser().getRoleId().toString()));
      }
      String routerPath = menu.getRouterPath();
      String dynamicPath = menu.getDynamicPath();
      if (StringUtils.isNotEmpty(dynamicPath)) {
        routerPath = dynamicPath;
        dynamicPath = menu.getRouterPath();
      }

      // 获取路由参数，用于同路径里面的tab切换
      Map<String, Object> routeParams = Optional.ofNullable(menu.getExpandFields())
        .map(m -> {
          String json = Convert.toStr(m.get("routeParams"));
          if (JSONUtil.isTypeJSON(json)) {
            return Convert.toStr(json);
          }
          return null;
        }).map(m -> {
          try {
            return JSONUtil.toBean(m, new TypeReference<Map<String, Object>>() {
            }, false);
          } catch (Exception e) {
            return null;
          }
        }).orElse(null);

      RouterVo router = new RouterVo();
      router.setName(menu.getComponentName());
      router.setPath(routerPath);
      router.setComponent(menu.getComponentInfo());
      router.setQuery(menu.getQueryParam());
      router.setMeta(new MetaVo(menu.getMenuId(), menu.getMenuName(), 1 != menu.getVisible(), 1 == menu.getIsCache(), menu.getIcon(), menu.getActiveMenu(), menu.getIsLink(), 1 == menu.getIsFrame(), dynamicPath, menu.getSubMenuWidth(), routeParams));
      List<SysMenu> cMenus = menu.getChildren();
      if (CollUtil.isNotEmpty(cMenus)) {
        router.setAlwaysShow(true);
        router.setRedirect(cMenus.get(0).getRouterPath());
        router.setChildren(buildMenus(cMenus));
      } else if (menu.isMenuFrame()) {
        router.setMeta(null);
        List<RouterVo> childrenList = new ArrayList<>();
        RouterVo children = new RouterVo();
        children.setPath(menu.getPath());
        children.setComponent(menu.getComponentInfo());
        children.setName(StringUtils.capitalize(menu.getPath()));
        children.setMeta(new MetaVo(menu.getMenuId(), menu.getMenuName(), 1 != menu.getVisible(), 1 == menu.getIsCache(), menu.getIcon(), menu.getActiveMenu(), menu.getIsLink(), 1 == menu.getIsFrame(), dynamicPath, menu.getSubMenuWidth(), routeParams));
        children.setQuery(menu.getQueryParam());
        childrenList.add(children);
        router.setChildren(childrenList);
      } else if (menu.getParentId().intValue() == 0 && menu.isInnerLink()) {
        router.setMeta(new MetaVo(menu.getMenuId(), menu.getMenuName(), menu.getIcon(), routeParams));
        router.setPath("/");
        List<RouterVo> childrenList = new ArrayList<>();
        RouterVo children = new RouterVo();
        routerPath = SysMenu.innerLinkReplaceEach(menu.getPath());
        children.setPath(routerPath);
        children.setComponent(UserConstants.INNER_LINK);
        children.setName(StringUtils.capitalize(routerPath));
        children.setMeta(new MetaVo(menu.getMenuId(), menu.getMenuName(), menu.getIcon(), menu.getPath(), routeParams));
        childrenList.add(children);
        router.setChildren(childrenList);
      }
      routers.add(router);
    }
    return routers;
  }

  /**
   * 构建前端所需要下拉树结构
   *
   * @param menus 菜单列表
   * @return 下拉树结构列表
   */
  @Override
  public List<Tree<Long>> buildMenuTreeSelect(List<SysMenuVo> menus) {
    if (CollUtil.isEmpty(menus)) {
      return CollUtil.newArrayList();
    }
    return TreeBuildUtils.build(menus, (menu, tree) -> tree.setId(menu.getMenuId()).setParentId(menu.getParentId()).setName(menu.getMenuName()).setWeight(menu.getOrderNum()));
  }

  /**
   * 根据菜单ID查询信息
   *
   * @param menuId 菜单ID
   * @return 菜单信息
   */
  @Override
  public SysMenuVo selectMenuById(Number menuId) {
    return baseMapper.selectVoById(menuId);
  }

  /**
   * 是否存在菜单子节点
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  @Override
  public boolean hasChildByMenuId(Long menuId) {
    return baseMapper.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getParentId, menuId));
  }

  /**
   * 查询菜单使用数量
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  @Override
  public boolean checkMenuExistRole(Long menuId) {
    return roleMenuMapper.exists(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, menuId));
  }

  /**
   * 新增保存菜单信息
   *
   * @param bo 菜单信息
   * @return 结果
   */
  @Override
  public int insertMenu(SysMenuBo bo) {
    SysMenu menu = MapstructUtils.convert(bo, SysMenu.class);
    return baseMapper.insert(menu);
  }

  /**
   * 修改保存菜单信息
   *
   * @param bo 菜单信息
   * @return 结果
   */
  @Override
  public int updateMenu(SysMenuBo bo) {
    SysMenu menu = MapstructUtils.convert(bo, SysMenu.class);
    return baseMapper.updateById(menu);
  }

  /**
   * 删除菜单管理信息
   *
   * @param menuId 菜单ID
   * @return 结果
   */
  @Override
  public int deleteMenuById(Long menuId) {
    return baseMapper.deleteById(menuId);
  }

  /**
   * 校验菜单名称是否唯一
   *
   * @param menu 菜单信息
   * @return 结果
   */
  @Override
  public boolean checkMenuNameUnique(SysMenuBo menu) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getMenuName, menu.getMenuName()).eq(SysMenu::getParentId, menu.getParentId()).ne(ObjectUtil.isNotNull(menu.getMenuId()), SysMenu::getMenuId, menu.getMenuId()));
    return !exist;
  }

  @NotNull
  private Function<Long, String> getParentNode() {
    var the = this;
    return new Function<>() {
      final Function<Long, String> self = this; // 使用局部变量引用

      /**
       * Function构造函数
       *
       * @param parentId    父级ID
       */
      @Override
      public String apply(Long parentId) {
        LambdaQueryWrapper<SysMenu> sysMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysMenuLambdaQueryWrapper.eq(SysMenu::getMenuId, parentId);
        SysMenu sysMenu = the.getById(parentId);
        String parentName = StringUtils.EMPTY;
        if (ObjectUtil.isNotNull(sysMenu)) {
          String _parentName = self.apply(sysMenu.getParentId()); // 递归获取父级菜单
          parentName = _parentName + (StringUtils.isNotEmpty(_parentName) ? "&gt;" : "") + sysMenu.getMenuName();
        }
        return parentName;
      }
    };
  }

  /**
   * 根据父节点的ID获取所有子节点
   *
   * @param list     分类表
   * @param parentId 传入的父节点ID
   * @return String
   */
  private List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
    List<SysMenu> returnList = new ArrayList<>();
    for (SysMenu t : list) {
      // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
      if (t.getParentId() == parentId) {
        recursionFn(list, t);
        returnList.add(t);
      }
    }
    return returnList;
  }

  /**
   * 递归列表
   */
  private void recursionFn(List<SysMenu> list, SysMenu t) {
    // 得到子节点列表
    List<SysMenu> childList = StreamUtils.filter(list, n -> n.getParentId().equals(t.getMenuId()));
    t.setChildren(childList);
    for (SysMenu tChild : childList) {
      // 判断是否有子节点
      if (list.stream().anyMatch(n -> n.getParentId().equals(tChild.getMenuId()))) {
        recursionFn(list, tChild);
      }
    }
  }

  @Override
  public R<Map<String, Object>> copyEditor(SaveEditorBo<SysMenuBo> saveEditorBo) {
    if (ObjectUtil.isEmpty(saveEditorBo.getIdValue())) {
      return R.fail("复制单据ID不存在");
    }

    SysMenu sysMenu = this.getById(saveEditorBo.getIdValue());

    // 保存主表
    sysMenu.setMenuId(null);
    this.save(sysMenu);

    Map<String, Object> mapResult = Optional.of(sysMenu).map(m -> {
      Map<String, Object> map = new HashMap<>();
      map.put("menuId", m.getMenuId());
      map.put("menuName", m.getMenuName());
      return map;
    }).get();

    return R.ok("复制成功", mapResult);
  }

  @Override
  public R<List<Map<String, Object>>> searchTree(String filterText) {
    Assert.isFalse(StringUtils.isEmpty(filterText), "搜索关键词不能为空");

    LambdaQueryWrapper<SysMenu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
    menuLambdaQueryWrapper.like(SysMenu::getMenuName, filterText).last("limit 10");
    List<SysMenu> menuList = this.list(menuLambdaQueryWrapper);

    List<Map<String, Object>> mapList = new ArrayList<>();
    for (var menu : menuList) {
      String menuName = menu.getMenuName();
      Function<Long, String> parentNode = getParentNode();
      String parentNameAll = parentNode.apply(menu.getParentId());
      parentNameAll = parentNameAll + "&gt;" + menuName;

      Map<String, Object> resultMap = new HashMap<>();
      resultMap.put("menuId", menu.getMenuId());
      resultMap.put("menuName", menu.getMenuName());
      resultMap.put("parentNameAll", parentNameAll);
      mapList.add(resultMap);
    }


    return R.ok(mapList);
  }

  @Override
  public R<List<SysMenuBaseVo>> getRootMenu() {
    if (StringUtils.equals(LoginHelper.getTenantId(), TenantConstants.DEFAULT_TENANT_ID)) {
      // 母版账套走标准菜单
      LambdaQueryWrapper<SysMenu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
      menuLambdaQueryWrapper
        .select(SysMenu::getMenuId, SysMenu::getMenuName)
        .eq(SysMenu::getParentId, NumberUtils.ZERO)
        .eq(SysMenu::getEnable, EnableEnum.ENABLE.getId())
        .eq(SysMenu::getVisible, EnableEnum.ENABLE.getId())
        .orderByDesc(SysMenu::getOrderNum);

      List<SysMenu> sysMenus = this.list(menuLambdaQueryWrapper);
      return R.ok(MapstructUtils.convert(sysMenus, SysMenuBaseVo.class));
    } else {
      // 其他账套走套餐菜单
      LambdaQueryWrapper<SysTenantMenu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
      menuLambdaQueryWrapper
        .select(SysTenantMenu::getMenuId, SysTenantMenu::getMenuName)
        .eq(SysTenantMenu::getPackageId, LoginHelper.getPackageId()) // 必须加上套餐条件
        .eq(SysTenantMenu::getParentId, NumberUtils.ZERO)
        .eq(SysTenantMenu::getEnable, EnableEnum.ENABLE.getId())
        .eq(SysTenantMenu::getVisible, EnableEnum.ENABLE.getId())
        .orderByDesc(SysTenantMenu::getOrderNum);

      ISysTenantMenuService sysTenantMenuService = SpringUtils.getBean(ISysTenantMenuService.class);
      List<SysTenantMenu> sysMenus = sysTenantMenuService.list(menuLambdaQueryWrapper);
      return R.ok(MapstructUtils.convert(sysMenus, SysMenuBaseVo.class));
    }
  }
}

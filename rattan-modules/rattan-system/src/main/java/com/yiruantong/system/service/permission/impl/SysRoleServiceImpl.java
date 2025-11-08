package com.yiruantong.system.service.permission.impl;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.system.domain.permission.SysRole;
import com.yiruantong.system.domain.permission.SysRoleDept;
import com.yiruantong.system.domain.permission.SysRoleMenu;
import com.yiruantong.system.domain.permission.SysUserRole;
import com.yiruantong.system.domain.permission.bo.SysRoleBo;
import com.yiruantong.system.domain.permission.vo.SysRoleVo;
import com.yiruantong.system.mapper.permission.SysRoleDeptMapper;
import com.yiruantong.system.mapper.permission.SysRoleMapper;
import com.yiruantong.system.mapper.permission.SysRoleMenuMapper;
import com.yiruantong.system.mapper.permission.SysUserRoleMapper;
import com.yiruantong.system.service.permission.ISysRoleService;
import com.yiruantong.system.service.permission.ISysUserRoleService;
import com.yiruantong.system.service.permission.ISysUserService;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.StreamUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 角色 业务层处理
 *
 * @author YiRuanTong
 */
@Priority(1)
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImplPlus<SysRoleMapper, SysRole, SysRoleVo, SysRoleBo> implements ISysRoleService, ITenantInitService {

  private final SysRoleMapper baseMapper;
  private final SysRoleMenuMapper roleMenuMapper;
  private final SysUserRoleMapper userRoleMapper;
  private final SysRoleDeptMapper roleDeptMapper;
  private final ISysUserRoleService sysUserRoleService;

  @Override
  public boolean afterDelete(Long[] ids) {
    // 删除用户角色关系
    LambdaQueryWrapper<SysUserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userRoleLambdaQueryWrapper.in(SysUserRole::getRoleId, ids);
    userRoleMapper.delete(userRoleLambdaQueryWrapper);

    return super.afterDelete(ids);
  }

  @Override
  public TableDataInfo<SysRoleVo> selectPageRoleList(SysRoleBo role, PageQuery pageQuery) {
    Page<SysRoleVo> page = baseMapper.selectPageRoleList(pageQuery.build(), this.buildQueryWrapper(role));
    return TableDataInfo.build(page);
  }

  /**
   * 根据条件分页查询角色数据
   *
   * @param role 角色信息
   * @return 角色数据集合信息
   */
  @Override
  public List<SysRoleVo> selectRoleList(SysRoleBo role) {
    return baseMapper.selectRoleList(this.buildQueryWrapper(role));
  }

  private Wrapper<SysRole> buildQueryWrapper(SysRoleBo bo) {
    Map<String, Object> params = bo.getParams();
    QueryWrapper<SysRole> wrapper = Wrappers.query();
    wrapper.eq("r.del_flag", UserConstants.ROLE_NORMAL)
      .eq(ObjectUtil.isNotNull(bo.getRoleId()), "r.role_id", bo.getRoleId())
      .like(StringUtils.isNotBlank(bo.getRoleName()), "r.role_name", bo.getRoleName())
      .eq(ObjectUtil.isNotNull(bo.getEnable()), "r.enable", bo.getEnable())
      .like(StringUtils.isNotBlank(bo.getRoleKey()), "r.role_key", bo.getRoleKey())
      .between(params.get("beginTime") != null && params.get("endTime") != null,
        "r.create_time", params.get("beginTime"), params.get("endTime"))
      .orderByAsc("r.order_num").orderByAsc("r.create_time");
    ;
    return wrapper;
  }

  /**
   * 根据用户ID查询角色
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  @Override
  public List<SysRoleVo> selectRolesByUserId(Long userId) {
    List<SysRoleVo> userRoles = baseMapper.selectRolePermissionByUserId(userId);
    List<SysRoleVo> roles = selectRoleAll();
    for (SysRoleVo role : roles) {
      for (SysRoleVo userRole : userRoles) {
        if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
          role.setFlag(true);
          break;
        }
      }
    }
    return roles;
  }

  /**
   * 根据用户ID查询权限
   *
   * @param userId 用户ID
   * @return 权限列表
   */
  @Override
  public Set<String> selectRolePermissionByUserId(Long userId) {
    List<SysRoleVo> perms = baseMapper.selectRolePermissionByUserId(userId);
    Set<String> permsSet = new HashSet<>();
    for (SysRoleVo perm : perms) {
      if (ObjectUtil.isNotNull(perm) && ObjectUtil.isNotNull(perm.getRoleKey())) {
        permsSet.addAll(StringUtils.splitList(perm.getRoleKey().trim()));
      }
    }
    return permsSet;
  }

  /**
   * 查询所有角色
   *
   * @return 角色列表
   */
  @Override
  public List<SysRoleVo> selectRoleAll() {
    return this.selectRoleList(new SysRoleBo());
  }

  /**
   * 根据用户ID获取角色选择框列表
   *
   * @param userId 用户ID
   * @return 选中角色ID列表
   */
  @Override
  public List<Long> selectRoleListByUserId(Long userId) {
    return baseMapper.selectRoleListByUserId(userId);
  }

  /**
   * 通过角色ID查询角色
   *
   * @param roleId 角色ID
   * @return 角色对象信息
   */
  @Override
  public SysRoleVo selectRoleById(Long roleId) {
    return baseMapper.selectRoleById(roleId);
  }

  /**
   * 校验角色名称是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  @Override
  public boolean checkRoleNameUnique(SysRoleBo role) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysRole>()
      .eq(SysRole::getRoleName, role.getRoleName())
      .ne(ObjectUtil.isNotNull(role.getRoleId()), SysRole::getRoleId, role.getRoleId()));
    return !exist;
  }

  /**
   * 校验角色权限是否唯一
   *
   * @param role 角色信息
   * @return 结果
   */
  @Override
  public boolean checkRoleKeyUnique(SysRoleBo role) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysRole>()
      .eq(SysRole::getRoleKey, role.getRoleKey())
      .ne(ObjectUtil.isNotNull(role.getRoleId()), SysRole::getRoleId, role.getRoleId()));
    return !exist;
  }

  /**
   * 校验角色是否允许操作
   *
   * @param role 角色信息
   */
  @Override
  public void checkRoleAllowed(SysRoleBo role) {
//    if (ObjectUtil.isNotNull(role.getRoleId()) && LoginHelper.isSuperAdmin(role.getRoleId())) {
//      throw new ServiceException("不允许操作超级管理员角色");
//    }
    // 新增不允许使用 管理员标识符
    if (ObjectUtil.isNull(role.getRoleId())
      && StringUtils.equalsAny(role.getRoleKey(),
      TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY)) {
      throw new ServiceException("不允许使用系统内置管理员角色标识符!");
    }
    // 修改不允许修改 管理员标识符
    if (ObjectUtil.isNotNull(role.getRoleId())) {
      SysRole sysRole = baseMapper.selectById(role.getRoleId());
      // 如果标识符不相等 判断为修改了管理员标识符
      if (!StringUtils.equals(sysRole.getRoleKey(), role.getRoleKey())
        && StringUtils.equalsAny(sysRole.getRoleKey(),
        TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY)) {
        throw new ServiceException("不允许修改系统内置管理员角色标识符!");
      }
    }
  }

  /**
   * 校验角色是否有数据权限
   *
   * @param roleId 角色id
   */
  @Override
  public void checkRoleDataScope(Long roleId) {
    if (ObjectUtil.isNull(roleId)) {
      return;
    }
    if (LoginHelper.isSuperAdmin()) {
      return;
    }
    List<SysRoleVo> roles = this.selectRoleList(new SysRoleBo(roleId));
    if (CollUtil.isEmpty(roles)) {
      throw new ServiceException("没有权限访问角色数据！");
    }

  }

  /**
   * 通过角色ID查询角色使用数量
   *
   * @param roleId 角色ID
   * @return 结果
   */
  @Override
  public long countUserRoleByRoleId(Long roleId) {
    return userRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
  }

  /**
   * 新增保存角色信息
   *
   * @param bo 角色信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int insertRole(SysRoleBo bo) {
    SysRole role = MapstructUtils.convert(bo, SysRole.class);
    // 新增角色信息
    baseMapper.insert(role);
    bo.setRoleId(role.getRoleId());
    return insertRoleMenu(bo);
  }

  /**
   * 修改保存角色信息
   *
   * @param bo 角色信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateRole(SysRoleBo bo) {
    SysRole role = MapstructUtils.convert(bo, SysRole.class);
    // 修改角色信息
    baseMapper.updateById(role);
    // 删除角色与菜单关联
    roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, role.getRoleId()));
    return insertRoleMenu(bo);
  }

  /**
   * 修改角色状态
   *
   * @param roleId 角色ID
   * @param enable 角色状态
   * @return 结果
   */
  @Override
  public int updateRoleStatus(Long roleId, byte enable) {
    if (UserConstants.ROLE_DISABLE == enable && this.countUserRoleByRoleId(roleId) > 0) {
      throw new ServiceException("角色已分配，不能禁用!");
    }
    return baseMapper.update(null,
      new LambdaUpdateWrapper<SysRole>()
        .set(SysRole::getEnable, enable)
        .eq(SysRole::getRoleId, roleId));
  }

  /**
   * 修改数据权限信息
   *
   * @param bo 角色信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int authDataScope(SysRoleBo bo) {
    SysRole role = MapstructUtils.convert(bo, SysRole.class);
    // 修改角色信息
    baseMapper.updateById(role);
    // 删除角色与部门关联
    roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, role.getRoleId()));
    // 新增角色和部门信息（数据权限）
    return insertRoleDept(bo);
  }

  /**
   * 新增角色菜单信息
   *
   * @param role 角色对象
   */
  private int insertRoleMenu(SysRoleBo role) {
    int rows = 1;
    // 新增用户与角色管理
    List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
    for (Long menuId : role.getMenuIds()) {
      SysRoleMenu rm = new SysRoleMenu();
      rm.setRoleId(role.getRoleId());
      rm.setMenuId(menuId);
      list.add(rm);
    }
    if (list.size() > 0) {
      rows = roleMenuMapper.insertBatch(list) ? list.size() : 0;
    }
    return rows;
  }

  /**
   * 新增角色部门信息(数据权限)
   *
   * @param role 角色对象
   */
  private int insertRoleDept(SysRoleBo role) {
    int rows = 1;
    // 新增角色与部门（数据权限）管理
    List<SysRoleDept> list = new ArrayList<SysRoleDept>();
    for (Long deptId : role.getDeptIds()) {
      SysRoleDept rd = new SysRoleDept();
      rd.setRoleId(role.getRoleId());
      rd.setDeptId(deptId);
      list.add(rd);
    }
    if (list.size() > 0) {
      rows = roleDeptMapper.insertBatch(list) ? list.size() : 0;
    }
    return rows;
  }

  /**
   * 通过角色ID删除角色
   *
   * @param roleId 角色ID
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteRoleById(Long roleId) {
    // 删除角色与菜单关联
    roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
    // 删除角色与部门关联
    roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, roleId));
    return baseMapper.deleteById(roleId);
  }

  /**
   * 批量删除角色信息
   *
   * @param roleIds 需要删除的角色ID
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteRoleByIds(Long[] roleIds) {
    for (Long roleId : roleIds) {
      // 检查角色是否已分配给用户
      if (countUserRoleByRoleId(roleId) > 0) {
        throw new ServiceException(String.format("角色ID[%d]已分配给用户，不允许删除!", roleId));
      }
      
      SysRole role = baseMapper.selectById(roleId);
      checkRoleAllowed(BeanUtil.toBean(role, SysRoleBo.class));
      checkRoleDataScope(roleId);
    }
    List<Long> ids = Arrays.asList(roleIds);
    // 删除角色与菜单关联
    roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, ids));
    // 删除角色与部门关联
    roleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().in(SysRoleDept::getRoleId, ids));
    return baseMapper.deleteBatchIds(ids);
  }

  /**
   * 取消授权用户角色
   *
   * @param userRole 用户和角色关联信息
   * @return 结果
   */
  @Override
  public int deleteAuthUser(SysUserRole userRole) {
    int rows = userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
      .eq(SysUserRole::getRoleId, userRole.getRoleId())
      .eq(SysUserRole::getUserId, userRole.getUserId()));
    if (rows > 0) {
      cleanOnlineUserByRole(userRole.getRoleId());
    }
    return rows;
  }

  /**
   * 批量取消授权用户角色
   *
   * @param roleId  角色ID
   * @param userIds 需要取消授权的用户数据ID
   * @return 结果
   */
  @Override
  public int deleteAuthUsers(Long roleId, Long[] userIds) {
    int rows = userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
      .eq(SysUserRole::getRoleId, roleId)
      .in(SysUserRole::getUserId, Arrays.asList(userIds)));
    if (rows > 0) {
      cleanOnlineUserByRole(roleId);
    }
    return rows;
  }

  /**
   * 批量选择授权用户角色
   *
   * @param roleId  角色ID
   * @param userIds 需要授权的用户数据ID
   * @return 结果
   */
  @Override
  public int insertAuthUsers(Long roleId, Long[] userIds) {
    // 新增用户与角色管理
    int rows = 1;
    List<SysUserRole> list = StreamUtils.toList(List.of(userIds), userId -> {
      SysUserRole ur = new SysUserRole();
      ur.setUserId(userId);
      ur.setRoleId(roleId);
      return ur;
    });
    if (CollUtil.isNotEmpty(list)) {
      rows = userRoleMapper.insertBatch(list) ? list.size() : 0;
    }
    if (rows > 0) {
      cleanOnlineUserByRole(roleId);
    }
    return rows;
  }

  @Override
  public void cleanOnlineUserByRole(Long roleId) {
    // 如果角色未绑定用户 直接返回
    Long num = userRoleMapper.selectCount(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, roleId));
    if (num == 0) {
      return;
    }
    List<String> keys = StpUtil.searchTokenValue("", 0, -1, false);
    if (CollUtil.isEmpty(keys)) {
      return;
    }
    // 角色关联的在线用户量过大会导致redis阻塞卡顿 谨慎操作
    keys.parallelStream().forEach(key -> {
      String token = StringUtils.substringAfterLast(key, ":");
      // 如果已经过期则跳过
      if (StpUtil.stpLogic.getTokenActiveTimeoutByToken(token) < -1) {
        return;
      }
      LoginUser loginUser = LoginHelper.getLoginUser(token);
      if (loginUser.getRoles().stream().anyMatch(r -> r.getRoleId().equals(roleId))) {
        try {
          StpUtil.logoutByTokenValue(token);
        } catch (NotLoginException ignored) {
        }
      }
    });
  }

  /**
   * 租户数据拷贝
   */
  @Override
  public void tenantCopy(Long fromPackageId, String tenantId, Long userId) {
    // 新增角色
    SysRole sysRole = new SysRole();
    sysRole.setRoleName("超级管理员");
    sysRole.setEnable(EnableEnum.ENABLE.getId());
    sysRole.setOrderNum(0);
    sysRole.setTenantId(tenantId);
    this.save(sysRole);

    // 新增用户和角色关系
    SysUserRole sysUserRole = new SysUserRole();
    sysUserRole.setRoleId(sysRole.getRoleId());
    sysUserRole.setUserId(userId);
    sysUserRoleService.save(sysUserRole);
  }

  /**
   * 清理数据
   */
  @Override
  public void tenantClean(String tenantId, Long userId) {
    ITenantInitService.super.tenantClean(tenantId, userId);
  }


  /**
   * 根据角色名称获取角色信息
   *
   * @param roleName 角色名称
   * @return 返回角色信息
   */
  @Override
  public SysRole getByName(String roleName) {
    LambdaQueryWrapper<SysRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysRole::getRoleName, roleName);
    return this.getOnly(lambdaQueryWrapper);
  }

}

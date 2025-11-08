package com.yiruantong.system.service.tenant.impl;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.common.core.config.RattanConfig;
import com.yiruantong.common.core.constant.CacheNames;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.TenantConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SexEnum;
import com.yiruantong.common.core.enums.user.UserTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.service.ITenantInitService;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.dto.UpdateBo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.redis.utils.CacheUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.core.SysConfig;
import com.yiruantong.system.domain.core.SysDictData;
import com.yiruantong.system.domain.core.SysDictType;
import com.yiruantong.system.domain.permission.*;
import com.yiruantong.system.domain.permission.dto.SaveTenantResultDto;
import com.yiruantong.system.domain.tenant.SysTenant;
import com.yiruantong.system.domain.tenant.SysTenantPackage;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.bo.SysTenantBo;
import com.yiruantong.system.domain.tenant.vo.SysTenantVo;
import com.yiruantong.system.mapper.core.SysConfigMapper;
import com.yiruantong.system.mapper.core.SysDictDataMapper;
import com.yiruantong.system.mapper.core.SysDictTypeMapper;
import com.yiruantong.system.mapper.permission.*;
import com.yiruantong.system.mapper.tenant.SysTenantMapper;
import com.yiruantong.system.mapper.tenant.SysTenantPackageMapper;
import com.yiruantong.system.service.permission.ISysUserService;
import com.yiruantong.system.service.tenant.ISysTenantProfileService;
import com.yiruantong.system.service.tenant.ISysTenantService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 租户Service业务层处理
 *
 * @author YiRuanTong
 */
@RequiredArgsConstructor
@Service
public class SysTenantServiceImpl extends ServiceImplPlus<SysTenantMapper, SysTenant, SysTenantVo, SysTenantBo> implements ISysTenantService {

  private final SysTenantMapper baseMapper;
  private final SysTenantPackageMapper tenantPackageMapper;
  private final SysUserMapper userMapper;
  private final SysDeptMapper deptMapper;
  private final SysRoleMapper roleMapper;
  private final SysRoleMenuMapper roleMenuMapper;
  private final SysRoleDeptMapper roleDeptMapper;
  private final SysUserRoleMapper userRoleMapper;
  private final SysDictTypeMapper dictTypeMapper;
  private final SysDictDataMapper dictDataMapper;
  private final SysConfigMapper configMapper;
  private final ISysUserService sysUserService;
  private final ISysTenantProfileService sysTenantProfileService;
  @Resource(name = "sysCodeRegularServiceImpl")
  private final ITenantInitService sysCodeRegularServiceImplTenantInitService;
  @Resource(name = "sysPrintTemplateServiceImpl")
  private final ITenantInitService sysPrintTemplateServiceImplTenantInitService;
  @Resource(name = "sysImportServiceImpl")
  private final ITenantInitService sysImportServiceImplTenantInitService;
  @Resource(name = "sysExportServiceImpl")
  private final ITenantInitService sysExportServiceImplTenantInitService;
  @Resource(name = "sysOssConfigServiceImpl")
  private final ITenantInitService sysOssConfigServiceImplTenantInitService;
  @Resource(name = "sysPageServiceImpl")
  private final ITenantInitService sysPageServiceImplTenantInitService;
  @Resource(name = "magicPageServiceImpl")
  private final ITenantInitService magicPageServiceImplTenantInitService;

  @Autowired
  private final RattanConfig rattanConfig;

  @Override
  public void beforeSaveEditor(SaveEditorBo<SysTenantBo> saveEditorBo) {
    if (StringUtils.isEmpty(saveEditorBo.getData().getMaster().getTenantId())) {
      String tenantId = saveEditorBo.getData().getMaster().getPackageId() + "-" + RandomUtil.randomStringUpper(6);
      saveEditorBo.getData().getMaster().setTenantId(tenantId);
    }

    super.beforeSaveEditor(saveEditorBo);
  }

  /**
   * 查询租户
   */
  @Override
  public SysTenantVo queryById(Long id) {
    return baseMapper.selectVoById(id);
  }

  /**
   * 基于租户ID查询租户
   */
//  @Cacheable(cacheNames = CacheNames.SYS_TENANT, key = "#tenantId")
  @Override
  public SysTenantVo queryByTenantId(String tenantId) {
    return baseMapper.selectVoOne(new LambdaQueryWrapper<SysTenant>().eq(SysTenant::getTenantId, tenantId));
  }

  /**
   * 新增租户
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean insertByBo(SysTenantBo bo, SaveTenantResultDto saveTenantResultDto) {

    SysTenant sysTenant = MapstructUtils.convert(bo, SysTenant.class);

    // 获取所有租户编号
    List<String> tenantIds = baseMapper.selectObjs(new LambdaQueryWrapper<SysTenant>().select(SysTenant::getTenantId), (x) -> {
      return Convert.toStr(x);
    });
    String tenantId = generateTenantId(bo, tenantIds);
    sysTenant.setTenantId(tenantId);
    boolean flag = baseMapper.insert(sysTenant) > 0;
    if (!flag) {
      throw new ServiceException("创建租户失败");
    }
    bo.setId(sysTenant.getId());

    // 根据套餐创建角色
    Long roleId = createTenantRole(tenantId, bo.getPackageId());

    // 创建部门: 公司名是部门名称
    SysDept dept = new SysDept();
    dept.setTenantId(tenantId);
    dept.setDeptName(bo.getCompanyName());
    dept.setParentId(Constants.TOP_PARENT_ID);
    dept.setAncestors(Constants.TOP_PARENT_ID.toString());
    deptMapper.insert(dept);
    Long deptId = dept.getDeptId();

    // 角色和部门关联表
//    SysRoleDept roleDept = new SysRoleDept();
//    roleDept.setRoleId(roleId);
//    roleDept.setDeptId(deptId);
//    roleDeptMapper.insert(roleDept);

    SysTenantProfile profile = sysTenantProfileService.getProfile(LoginHelper.getPhoneNumber());
    // 创建系统用户
    SysUser user = getSysUser(tenantId, deptId, profile);
    userMapper.insert(user);

    //新增系统用户后，默认当前用户为部门的负责人
    SysDept sd = new SysDept();
    sd.setLeaderId(user.getUserId());
    sd.setDeptId(deptId);
    deptMapper.updateById(sd);

    // 用户和角色关联表
    SysUserRole userRole = new SysUserRole();
    userRole.setUserId(user.getUserId());
    userRole.setRoleId(roleId);
    userRoleMapper.insert(userRole);

    String defaultTenantId = TenantConstants.DEFAULT_TENANT_ID;
    List<SysDictType> dictTypeList = dictTypeMapper.selectList(
      new LambdaQueryWrapper<SysDictType>().eq(SysDictType::getTenantId, defaultTenantId));
    List<SysDictData> dictDataList = dictDataMapper.selectList(
      new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getTenantId, defaultTenantId));
    for (SysDictType dictType : dictTypeList) {
      dictType.setDictId(null);
      dictType.setTenantId(tenantId);
    }
    for (SysDictData dictData : dictDataList) {
      dictData.setDictCode(null);
      dictData.setTenantId(tenantId);
    }
    dictTypeMapper.insertBatch(dictTypeList);
    dictDataMapper.insertBatch(dictDataList);

    List<SysConfig> sysConfigList = configMapper.selectList(
      new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getTenantId, defaultTenantId));
    for (SysConfig config : sysConfigList) {
      config.setConfigId(null);
      config.setTenantId(tenantId);
    }
    configMapper.insertBatch(sysConfigList);

    saveTenantResultDto.setSysTenant(sysTenant);
    saveTenantResultDto.setSysDept(dept);
    saveTenantResultDto.setSysUser(user);
    return true;
  }

  @NotNull
  private SysUser getSysUser(String tenantId, Long deptId, SysTenantProfile profile) {
    SysUser user = new SysUser();
    user.setUserType(UserTypeEnum.SYS_USER.getUserType());
    user.setTenantId(tenantId);
    user.setDeptId(deptId);
    user.setEnable(EnableEnum.ENABLE.getId());
    user.setEmail(profile.getEmail());
    user.setIsAdministrator(EnableEnum.ENABLE.getId());
    user.setNickName(profile.getNickName());
    user.setPassword(profile.getPassword());
    user.setPhoneNumber(profile.getPhoneNumber());
    user.setUserName(StringUtils.defaultIfBlank(profile.getUserName(), profile.getPhoneNumber()));
    user.setSex(SexEnum.MALE.getName());
    return user;
  }

  /**
   * 生成租户id
   *
   * @param tenantIds 已有租户id列表
   * @return 租户id
   */
  private String generateTenantId(SysTenantBo bo, List<String> tenantIds) {
    // 随机生成6位
    String numbers = bo.getPackageId() + "-" + RandomUtil.randomStringUpper(6);
    // 判断是否存在，如果存在则重新生成
    if (tenantIds.contains(numbers)) {
      generateTenantId(bo, tenantIds);
    }
    return numbers;
  }

  /**
   * 根据租户菜单创建租户角色
   *
   * @param tenantId  租户编号
   * @param packageId 租户套餐id
   * @return 角色id
   */
  private Long createTenantRole(String tenantId, Long packageId) {
    // 获取租户套餐
    SysTenantPackage tenantPackage = tenantPackageMapper.selectById(packageId);
    if (ObjectUtil.isNull(tenantPackage)) {
      throw new ServiceException("套餐不存在");
    }
    // 获取套餐菜单id
//    List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);

    // 创建角色
    SysRole role = new SysRole();
    role.setTenantId(tenantId);
    role.setRoleName(TenantConstants.TENANT_ADMIN_ROLE_NAME);
    role.setRoleKey(TenantConstants.TENANT_ADMIN_ROLE_KEY);
    role.setOrderNum(1);
    role.setEnable(TenantConstants.NORMAL);
    roleMapper.insert(role);
    Long roleId = role.getRoleId();

    // 创建角色菜单
//    List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
//    menuIds.forEach(menuId -> {
//      SysRoleMenu roleMenu = new SysRoleMenu();
//      roleMenu.setRoleId(roleId);
//      roleMenu.setMenuId(menuId);
//      roleMenus.add(roleMenu);
//    });
//    roleMenuMapper.insertBatch(roleMenus);

    return roleId;
  }


  /**
   * 修改租户状态
   *
   * @param bo 租户信息
   * @return 结果
   */
  @CacheEvict(cacheNames = CacheNames.SYS_TENANT, key = "#bo.tenantId")
  @Override
  public int updateTenantStatus(SysTenantBo bo) {
    SysTenant tenant = MapstructUtils.convert(bo, SysTenant.class);
    return baseMapper.updateById(tenant);
  }

  /**
   * 校验租户是否允许操作
   *
   * @param tenantId 租户ID
   */
  @Override
  public void checkTenantAllowed(String tenantId) {
    if (ObjectUtil.isNotNull(tenantId) && TenantConstants.DEFAULT_TENANT_ID.equals(tenantId)) {
      throw new ServiceException("不允许操作管理租户");
    }
  }

  /**
   * 校验账号余额
   */
  @Override
  public boolean checkAccountBalance(String tenantId) {
    SysTenantVo tenant = SpringUtils.getAopProxy(this).queryByTenantId(tenantId);
    // 如果余额为-1代表不限制
    if (tenant.getAccountCount() == -1) {
      return true;
    }
    Long userNumber = userMapper.selectCount(new LambdaQueryWrapper<>());
    // 如果余额大于0代表还有可用名额
    return tenant.getAccountCount() - userNumber > 0;
  }

  /**
   * 校验有效期
   */
  @Override
  public boolean checkExpireTime(String tenantId) {
    SysTenantVo tenant = SpringUtils.getAopProxy(this).queryByTenantId(tenantId);
    // 如果未设置过期时间代表不限制
    if (ObjectUtil.isNull(tenant.getExpireTime())) {
      return true;
    }
    // 如果当前时间在过期时间之前则通过
    return new Date().before(tenant.getExpireTime());
  }

  /**
   * 同步租户套餐
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean syncTenantPackage(String tenantId, Long packageId) {
    SysTenantPackage tenantPackage = tenantPackageMapper.selectById(packageId);
    List<SysRole> roles = roleMapper.selectList(
      new LambdaQueryWrapper<SysRole>().eq(SysRole::getTenantId, tenantId));
    List<Long> roleIds = new ArrayList<>(roles.size() - 1);
    List<Long> menuIds = StringUtils.splitTo(tenantPackage.getMenuIds(), Convert::toLong);
    roles.forEach(item -> {
      if (TenantConstants.TENANT_ADMIN_ROLE_KEY.equals(item.getRoleKey())) {
        List<SysRoleMenu> roleMenus = new ArrayList<>(menuIds.size());
        menuIds.forEach(menuId -> {
          SysRoleMenu roleMenu = new SysRoleMenu();
          roleMenu.setRoleId(item.getRoleId());
          roleMenu.setMenuId(menuId);
          roleMenus.add(roleMenu);
        });
        roleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, item.getRoleId()));
        roleMenuMapper.insertBatch(roleMenus);
      } else {
        roleIds.add(item.getRoleId());
      }
    });
    if (!roleIds.isEmpty()) {
      roleMenuMapper.delete(
        new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds).notIn(!menuIds.isEmpty(), SysRoleMenu::getMenuId, menuIds));
    }
    return true;
  }

  /**
   * 判断是否存多个账套
   *
   * @param mobile      手机号
   * @param refTenantId
   * @param password
   * @return boolean
   */
  @Override
  public boolean isExistMultiTenant(String mobile, AtomicReference<String> refTenantId, String password) {
    boolean multiTenant;
    TenantHelper.enableIgnore();
    LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper
      .eq(SysUser::getPhoneNumber, mobile)
      .eq(SysUser::getRelationTenant, EnableEnum.ENABLE.getId());
    List<SysUser> userList = sysUserService.list(userLambdaQueryWrapper);
    TenantHelper.disableIgnore();

    String tenantId;
    if (userList.size() == 1) {
      tenantId = userList.get(0).getTenantId();
      multiTenant = false;
    } else if (userList.size() > 1) {
      tenantId = userList.get(0).getTenantId();
      multiTenant = true;
    } else {
      tenantId = TenantConstants.NONE_TENANT_ID;
      multiTenant = false;
    }

    // 采用用户名登录
    if (StringUtils.equals(tenantId, TenantConstants.NONE_TENANT_ID)) {
      TenantHelper.enableIgnore();
      userLambdaQueryWrapper = new LambdaQueryWrapper<>();
      userLambdaQueryWrapper
        .eq(SysUser::getRelationTenant, EnableEnum.ENABLE.getId())
        .and(a -> {
          a.eq(SysUser::getPhoneNumber, mobile)
            .or()
            .eq(SysUser::getUserName, mobile);
        });
      // 同时密码验证也正确
      userList = sysUserService.list(userLambdaQueryWrapper).stream().filter(f -> BCrypt.checkpw(password, f.getPassword())).toList();
      TenantHelper.disableIgnore();

      // 用户编号必须唯一
      if (userList.size() == 1) {
        tenantId = userList.get(0).getTenantId();
        multiTenant = false;
      } else if (userList.size() > 1) {
        tenantId = userList.get(0).getTenantId();
        multiTenant = true;
      }
    }
    refTenantId.set(tenantId);
    return multiTenant; // 没有账套和多个账套都算为多账套，目的进入账套选择页面
  }


  /**
   * 获取我关联的租户账套
   *
   * @param mobile   手机号
   * @param password
   * @return 返回账套信息列表
   */
  @Override
  public List<SysTenantVo> getMyTenantList(String mobile, String password) {
    // 验证API不允许登录系统
    LoginHelper.checkConsoleUser();

    LambdaQueryWrapper<SysUser> userLambdaQueryWrapper;
    List<SysUser> userList;
    TenantHelper.enableIgnore();
    if (Validator.isMobile(mobile)) {
      userLambdaQueryWrapper = new LambdaQueryWrapper<>();
      userLambdaQueryWrapper.eq(SysUser::getPhoneNumber, mobile)
        .eq(SysUser::getRelationTenant, EnableEnum.ENABLE.getId());
      userList = sysUserService.list(userLambdaQueryWrapper);
    } else {
      // 用户名登录
      userLambdaQueryWrapper = new LambdaQueryWrapper<>();
      userLambdaQueryWrapper.eq(SysUser::getUserName, mobile)
        .eq(SysUser::getRelationTenant, EnableEnum.ENABLE.getId());
      userList = sysUserService.list(userLambdaQueryWrapper).stream().filter(f -> BCrypt.checkpw(password, f.getPassword())).toList();
    }
    TenantHelper.disableIgnore();

    LambdaQueryWrapper<SysTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
    List<String> tenantIdlist = new ArrayList<>(userList.stream().map(SysUser::getTenantId).toList());
    if (tenantIdlist.isEmpty()) tenantIdlist.add("-1");
    tenantLambdaQueryWrapper.in(SysTenant::getTenantId, tenantIdlist);

    return this.selectList(tenantLambdaQueryWrapper);
  }

  @Override
  public Map<String, Object> getTenantInfo(HttpServletRequest request) throws URISyntaxException {
    String tenantId = LoginHelper.getTenantId();
    SysTenantVo sysTenantVo;
    if (StringUtils.isEmpty(tenantId) || StringUtils.equals(tenantId, "-1")) {
      // 获取域名
      String domainName;
      String referer = request.getHeader("referer");
      if (StringUtils.isNotBlank(referer)) {
        // 这里从referer中取值是为了本地使用hosts添加虚拟域名，方便本地环境调试
        domainName = referer.split("//")[1].split("/")[0];
      } else {
        domainName = new URI(request.getRequestURL().toString()).getHost();
      }

      LambdaQueryWrapper<SysTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
      tenantLambdaQueryWrapper.eq(SysTenant::getDomain, domainName);
      sysTenantVo = this.selectOne(tenantLambdaQueryWrapper);
      if (ObjectUtil.isNull(sysTenantVo)) {
        tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isEmpty(tenantId) || StringUtils.equals(tenantId, "-1")) {
          tenantId = TenantConstants.DEFAULT_TENANT_ID;
        }
        tenantLambdaQueryWrapper.eq(SysTenant::getTenantId, tenantId);
        sysTenantVo = this.selectOne(tenantLambdaQueryWrapper);
      }
    } else {
      sysTenantVo = this.queryByTenantId(tenantId);
    }
    Map<String, Object> map = new HashMap<>();
    map.put("tenantId", sysTenantVo.getTenantId());
    map.put("companyName", sysTenantVo.getCompanyName());
    map.put("address", sysTenantVo.getAddress());
    map.put("logoLong", sysTenantVo.getLogoLong());
    map.put("logoShort", sysTenantVo.getLogoShort());
    map.put("sysShortName", sysTenantVo.getSysShortName());
    map.put("sysFullName", sysTenantVo.getSysFullName());
    map.put("expandFields", sysTenantVo.getExpandFields());
    map.put("isOpenReg", rattanConfig.isOpenReg());
    map.put("isOpenMer", rattanConfig.isOpenMer());

    return map;
  }

  /**
   * 注销租户应用
   *
   * @param tenantId 租户ID
   * @return
   */
  @Override
  public R<Void> cancelApp(String tenantId) {
    LambdaQueryWrapper<SysTenant> tenantLambdaQueryWrapper = new LambdaQueryWrapper<>();
    tenantLambdaQueryWrapper.eq(SysTenant::getTenantId, tenantId);
    SysTenant sysTenant = this.getOne(tenantLambdaQueryWrapper);
    // 只有账套拥有者才可以执行注销操作
    if (!StringUtils.equals(sysTenant.getContactPhone(), LoginHelper.getPhoneNumber())) {
      throw new ServiceException("没有权限进行操作！");
    }

    if (StringUtils.contains(tenantId, TenantConstants.DEFAULT_TENANT_ID)) {
      throw new ServiceException("标准账套不允许注销！");
    }

    boolean result = this.remove(tenantLambdaQueryWrapper);
    return result ? R.ok() : R.fail();
  }

  @Override
  public R<Void> syncBasicData(String targetTenantId, List<Long> menuIdList) {
    for (Long item : menuIdList) {
      MenuEnum menuEnum = MenuEnum.getEnumById(item.intValue());
      if (ObjectUtil.isNull(menuEnum)) continue;

      switch (menuEnum) {
        case MENU_1055: // 单据自动编码
          sysCodeRegularServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        case MENU_106: // 打印模板
          sysPrintTemplateServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        case MENU_1502: // 导出设置
          sysImportServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        case MENU_1058: // 打印模板
          sysExportServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        case MENU_2233: // OSS配置管理
          sysOssConfigServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        case MENU_2083: // 页面装修
          sysPageServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        case MENU_2263: // magic-ui设计器
          magicPageServiceImplTenantInitService.tenantCopy(LoginHelper.getPackageId(), targetTenantId, LoginHelper.getUserId());
          break;
        default:
          break;
      }
    }

    return R.ok();
  }

  /**
   * 更新数据
   *
   * @param updateBo 修改信息
   * @return boolean
   */
  @Override
  public boolean update(UpdateBo updateBo) {
    CacheUtils.evict(CacheNames.SYS_TENANT, updateBo.getQueryBoList().stream().filter(f -> StringUtils.equals(f.getColumn(), "tenantId")).findAny().orElse(new QueryBo()).getValues());
    return super.update(updateBo);
  }
}

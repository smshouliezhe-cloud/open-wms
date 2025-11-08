package com.yiruantong.system.service.permission.impl;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import com.xingyuv.captcha.model.common.ResponseModel;
import com.xingyuv.captcha.model.vo.CaptchaVO;
import com.xingyuv.captcha.service.CaptchaService;
import com.yiruantong.common.core.config.RattanConfig;
import com.yiruantong.common.core.constant.*;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.ModifyEmailBody;
import com.yiruantong.common.core.domain.model.ModifyPhoneNumberBody;
import com.yiruantong.common.core.enums.base.EnableEnum;
import com.yiruantong.common.core.enums.base.MenuEnum;
import com.yiruantong.common.core.enums.base.SysUserEnum;
import com.yiruantong.common.core.enums.user.UserTypeEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.exception.user.CaptchaException;
import com.yiruantong.common.core.exception.user.CaptchaExpireException;
import com.yiruantong.common.core.exception.user.UserException;
import com.yiruantong.common.core.service.UserService;
import com.yiruantong.common.core.utils.*;
import com.yiruantong.common.mail.utils.MailUtils;
import com.yiruantong.common.mybatis.core.domain.bo.SaveEditorBo;
import com.yiruantong.common.mybatis.core.domain.vo.EditorVo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DBUtils;
import com.yiruantong.common.redis.utils.CacheUtils;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.domain.permission.*;
import com.yiruantong.system.domain.permission.bo.SysUserBo;
import com.yiruantong.system.domain.permission.vo.SysPostVo;
import com.yiruantong.system.domain.permission.vo.SysRoleVo;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import com.yiruantong.system.domain.tenant.SysTenantProfile;
import com.yiruantong.system.domain.tenant.bo.SysTenantProfileBo;
import com.yiruantong.system.mapper.permission.*;
import com.yiruantong.system.service.permission.*;
import com.yiruantong.system.service.tenant.ISysTenantProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author YiRuanTong
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends ServiceImplPlus<SysUserMapper, SysUser, SysUserVo, SysUserBo> implements ISysUserService, UserService {

  private final SysUserMapper baseMapper;
  private final SysDeptMapper deptMapper;
  private final SysRoleMapper roleMapper;
  private final SysPostMapper postMapper;
  private final SysUserRoleMapper userRoleMapper;
  private final SysUserPostMapper userPostMapper;
  private final ISysTenantProfileService sysTenantProfileService;
  private final CaptchaService captchaService;
  private final ISysUserPostService sysUserPostService;
  private final ISysPostService sysPostService;
  private final ISysRoleService sysRoleService;
  private final ISysUserRoleService sysUserRoleService;
  private final RattanConfig rattanConfig;


  private EditorVo<SysUserVo> editor;

  //#region 分页查询前事件

  /**
   * 分页查询前事件
   *
   * @param pageQuery 加载参数
   */
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    SysDeptPostServiceImpl.treeQuery(pageQuery, deptMapper);
  }
  //#endregion

  //#region 分页查询后事件

  /**
   * 分页查询后事件
   *
   * @param pageQuery 加载参数
   */
  @Override
  public void afterPageQuery(PageQuery pageQuery, IPage<SysUserVo> iPage) {
    //#region 角色处理
    LambdaQueryWrapper<SysUserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userRoleLambdaQueryWrapper.in(SysUserRole::getUserId, iPage.getRecords().stream().map(SysUserVo::getUserId).toArray());
    List<SysUserRole> sysUserRoleList = userRoleMapper.selectList(userRoleLambdaQueryWrapper);

    LambdaQueryWrapper<SysRole> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    List<Long> roleIdList = sysUserRoleList.stream().map(SysUserRole::getRoleId).distinct().toList();
    roleLambdaQueryWrapper.in(SysRole::getRoleId, CollUtil.defaultIfEmpty(roleIdList, List.of(0L)));
    List<SysRole> sysRoleList = roleMapper.selectList(roleLambdaQueryWrapper);

    for (var item : iPage.getRecords()) {
      List<Long> longList = sysUserRoleList.stream().filter(f -> NumberUtils.equals(f.getUserId(), item.getUserId())).map(SysUserRole::getRoleId).toList();
      item.setRoleIds(longList.toArray(Long[]::new)); // 获取角色ID集合

      String roleNames = sysRoleList.stream().filter(f -> longList.contains(f.getRoleId())).map(SysRole::getRoleName).collect(Collectors.joining(","));
      item.setRoleNames(roleNames); // 获取角色名称集合
    }
    //#endregion

    //#region 岗位处理
    LambdaQueryWrapper<SysUserPost> userPostLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userPostLambdaQueryWrapper.in(SysUserPost::getUserId, iPage.getRecords().stream().map(SysUserVo::getUserId).toArray());
    List<SysUserPost> sysUserPostList = sysUserPostService.list(userPostLambdaQueryWrapper);

    LambdaQueryWrapper<SysPost> postLambdaQueryWrapper = new LambdaQueryWrapper<>();
    List<Long> postIdList = sysUserPostList.stream().map(SysUserPost::getPostId).distinct().toList();
    postLambdaQueryWrapper.in(SysPost::getPostId, CollUtil.defaultIfEmpty(postIdList, List.of(0L)));
    List<SysPost> sysPostList = sysPostService.list(postLambdaQueryWrapper);

    for (var item : iPage.getRecords()) {
      List<Long> longList = sysUserPostList.stream().filter(f -> NumberUtils.equals(f.getUserId(), item.getUserId())).map(SysUserPost::getPostId).toList();
      item.setPostIds(longList.toArray(Long[]::new)); // 获取角色ID集合

      String postNames = sysPostList.stream().filter(f -> longList.contains(f.getPostId())).map(SysPost::getPostName).collect(Collectors.joining(","));
      item.setPostNames(postNames); // 获取岗位名称集合
    }
    //#endregion
  }
  //#endregion

  //#region 编辑后事件

  /**
   * 编辑后事件
   *
   * @param editor 编辑内容
   */
  @Override
  public void afterEditor(EditorVo<SysUserVo> editor) {
    UserService userService = SpringUtils.getBean(UserService.class);
    // 获取当前用户角色ID
    List<Long> longList = userService.selectRoleListByUserId(editor.getMaster().getUserId());
    editor.getMaster().setRoleIds(longList.toArray(Long[]::new));

    // 获取当前用户角色ID
    List<Long> postList = userService.selectPostListByUserId(editor.getMaster().getUserId());
    editor.getMaster().setPostIds(postList.toArray(Long[]::new));
  }
  //#endregion

  //#region 保存前事件

  /**
   * 保存前事件
   *
   * @param saveEditorBo 保存提交bo数据
   */
  @Override
  public void beforeSaveEditor(SaveEditorBo<SysUserBo> saveEditorBo) {
    //#region 判断用户不允许重复
    if (StringUtils.isBlank(saveEditorBo.getData().getMaster().getUserName())) {
      throw new ServiceException("账号不允许为空！");
    }

    LambdaQueryWrapper<SysUser> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper.eq(SysUser::getUserName, saveEditorBo.getData().getMaster().getUserName());
    // 如果用户信息Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getUserId())) {
      userLambdaQueryWrapper.eq(SysUser::getUserName, saveEditorBo.getData().getMaster().getUserName())
        .ne(SysUser::getUserId, saveEditorBo.getData().getMaster().getUserId());
    }
    long count = this.count(userLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("用户名称【" + saveEditorBo.getData().getMaster().getUserName() + "】已存在，不允许重复！");
    }
    //#endregion

    //#region 判断手机号不允许重复
    if (StringUtils.isBlank(saveEditorBo.getData().getMaster().getPhoneNumber())) {
      throw new ServiceException("手机号不允许为空！");
    }

    userLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userLambdaQueryWrapper.eq(SysUser::getPhoneNumber, saveEditorBo.getData().getMaster().getPhoneNumber());
    // 如果用户信息Id不为空
    if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getUserId())) {
      userLambdaQueryWrapper.eq(SysUser::getPhoneNumber, saveEditorBo.getData().getMaster().getPhoneNumber())
        .ne(SysUser::getUserId, saveEditorBo.getData().getMaster().getUserId());
    }
    count = this.count(userLambdaQueryWrapper);
    if (count > 0) {
      throw new ServiceException("手机号【" + saveEditorBo.getData().getMaster().getPhoneNumber() + "】已存在，不允许重复！");
    }
    if (ObjectUtil.isNotNull(saveEditorBo.getData().getMaster().getUserId()) && saveEditorBo.getData().getMaster().getUserId() > 0) {
      // 修改手机号
      SysUser sysUser = this.getById(saveEditorBo.getData().getMaster().getUserId());
      if (!StringUtils.equals(sysUser.getPhoneNumber(), saveEditorBo.getData().getMaster().getPhoneNumber())) {
        LambdaUpdateWrapper<SysTenantProfile> profileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        profileLambdaUpdateWrapper.set(SysTenantProfile::getPhoneNumber, saveEditorBo.getData().getMaster().getPhoneNumber())
          .eq(SysTenantProfile::getPhoneNumber, sysUser.getPhoneNumber());
        sysTenantProfileService.update(profileLambdaUpdateWrapper);
      }
    }
    //#endregion

    //#region 判断email不允许重复
    if (!StringUtils.isBlank(saveEditorBo.getData().getMaster().getEmail())) {
      userLambdaQueryWrapper = new LambdaQueryWrapper<>();
      userLambdaQueryWrapper.eq(SysUser::getEmail, saveEditorBo.getData().getMaster().getEmail());
      // 如果用户信息Id不为空
      if (ObjectUtil.isNotEmpty(saveEditorBo.getData().getMaster().getUserId())) {
        userLambdaQueryWrapper.eq(SysUser::getEmail, saveEditorBo.getData().getMaster().getEmail())
          .ne(SysUser::getUserId, saveEditorBo.getData().getMaster().getUserId());
      }
      count = this.count(userLambdaQueryWrapper);
      if (count > 0) {
        throw new ServiceException("email【" + saveEditorBo.getData().getMaster().getEmail() + "】已存在，不允许重复！");
      }
    }
    //#endregion

    super.beforeSaveEditor(saveEditorBo);
  }
  //#endregion

  //#region 保存后事件

  /**
   * 保存后事件
   *
   * @param saveEditorBo 保存提交bo数据
   * @param editor
   */
  @Override
  public void afterSaveEditor(SaveEditorBo<SysUserBo> saveEditorBo, EditorVo<SysUserVo> editor) {
    saveEditorBo.getData().getMaster().setUserId(editor.getMaster().getUserId());
    // 保存角色
    insertUserRole(saveEditorBo.getData().getMaster(), true);

    UserService sysUserService = SpringUtils.getBean(UserService.class);
    // 获取当前用户角色ID
    List<Long> longList = sysUserService.selectRoleListByUserId(editor.getMaster().getUserId());
    editor.getMaster().setRoleIds(longList.toArray(Long[]::new));

    // 保存岗位
    insertUserPost(saveEditorBo.getData().getMaster(), true);

    // 获取当前用户角色ID
    List<Long> postIdList = sysUserService.selectPostListByUserId(editor.getMaster().getUserId());
    editor.getMaster().setPostIds(postIdList.toArray(Long[]::new));

    // 是否新建
    boolean isAdd = saveEditorBo.isAdd();
    String psd = TenantConstants.DEFAULT_TENANT_ID;
    // 新建后自动生成初始密码000000
    if (isAdd) {
      this.resetUserPwd(saveEditorBo.getData().getMaster().getUserId(), 0, BCrypt.hashpw(psd));
    }

    ISysDeptService bean = SpringUtils.getBean(ISysDeptService.class);
    var dept = bean.getDeptInfo(editor.getMaster().getDeptId());
    if (ObjectUtil.isNotNull(dept)) {
      // 修改数据
      LambdaUpdateWrapper<SysUser> lambda = new UpdateWrapper<SysUser>().lambda();
      lambda.set(SysUser::getFullDeptName, dept.getFullDeptName())
        .set(SysUser::getFullDeptId, dept.getFullDeptId())
        .eq(SysUser::getUserId, editor.getMaster().getUserId());
      this.update(lambda);//提交
    }

    //#region 判断手机号是否在profile中存在
    if (StringUtils.isBlank(saveEditorBo.getData().getMaster().getPhoneNumber())) {
      throw new ServiceException("手机号不允许为空！");
    }

    LambdaQueryWrapper<SysTenantProfile> profileLambdaQueryWrapper = new LambdaQueryWrapper<>();
    profileLambdaQueryWrapper.eq(SysTenantProfile::getPhoneNumber, saveEditorBo.getData().getMaster().getPhoneNumber());
    if (sysTenantProfileService.exists(profileLambdaQueryWrapper)) {
      LambdaUpdateWrapper<SysTenantProfile> profileLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
      profileLambdaUpdateWrapper
        .set(SysTenantProfile::getUserName, saveEditorBo.getData().getMaster().getUserName())
        .set(SysTenantProfile::getNickName, saveEditorBo.getData().getMaster().getNickName())
        .set(SysTenantProfile::getEmail, saveEditorBo.getData().getMaster().getEmail())
        .set(SysTenantProfile::getSex, saveEditorBo.getData().getMaster().getSex())
        .set(SysTenantProfile::getAvatar, saveEditorBo.getData().getMaster().getAvatar())
        .eq(SysTenantProfile::getPhoneNumber, saveEditorBo.getData().getMaster().getPhoneNumber());
      sysTenantProfileService.update(profileLambdaUpdateWrapper);
    } else {
      SysTenantProfile profile = BeanUtil.copyProperties(saveEditorBo.getData().getMaster(), SysTenantProfile.class);
      sysTenantProfileService.save(profile);
    }
    //#endregion
  }
  //#endregion

  @Override
  public boolean afterDelete(Long[] ids) {
    // 删除用户角色关系
    LambdaQueryWrapper<SysUserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userRoleLambdaQueryWrapper.in(SysUserRole::getUserId, ids);
    userRoleMapper.delete(userRoleLambdaQueryWrapper);

    // 删除用户岗位关系
    LambdaQueryWrapper<SysUserPost> userPostLambdaQueryWrapper = new LambdaQueryWrapper<>();
    userPostLambdaQueryWrapper.in(SysUserPost::getUserId, ids);
    userPostMapper.delete(userPostLambdaQueryWrapper);

    return super.afterDelete(ids);
  }

  @Override
  public TableDataInfo<SysUserVo> selectPageUserList(SysUserBo user, PageQuery pageQuery) {
    Page<SysUserVo> page = baseMapper.selectPageUserList(pageQuery.build(), this.buildQueryWrapper(user));
    return TableDataInfo.build(page);
  }

  //#region 根据条件分页查询用户列表

  /**
   * 根据条件分页查询用户列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  public List<SysUserVo> selectUserList(SysUserBo user) {
    return baseMapper.selectUserList(this.buildQueryWrapper(user));
  }
  //#endregion

  private Wrapper<SysUser> buildQueryWrapper(SysUserBo user) {
    Map<String, Object> params = user.getParams();
    QueryWrapper<SysUser> wrapper = Wrappers.query();
    wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
      .eq(ObjectUtil.isNotNull(user.getUserId()), "u.user_id", user.getUserId())
      .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
      .eq(ObjectUtil.isNotNull(user.getEnable()), "u.status", user.getEnable())
      .like(StringUtils.isNotBlank(user.getPhoneNumber()), "u.phone_number", user.getPhoneNumber())
      .between(params.get("beginTime") != null && params.get("endTime") != null,
        "u.create_time", params.get("beginTime"), params.get("endTime"))
      .in("u.dept_id", user.getAllChildrenId())
      .orderByAsc("u.user_id");
    return wrapper;
  }

  //#region 根据条件分页查询已分配用户角色列表

  /**
   * 根据条件分页查询已分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  public TableDataInfo<SysUserVo> selectAllocatedList(SysUserBo user, PageQuery pageQuery) {
    QueryWrapper<SysUser> wrapper = Wrappers.query();
    wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
//      .eq(ObjectUtil.isNotNull(user.getRoleId()), "r.role_id", user.getRoleId())
      .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
      .eq(ObjectUtil.isNotNull(user.getEnable()), "u.status", user.getEnable())
      .like(StringUtils.isNotBlank(user.getPhoneNumber()), "u.phone_number", user.getPhoneNumber())
      .orderByAsc("u.user_id");
    Page<SysUserVo> page = baseMapper.selectAllocatedList(pageQuery.build(), wrapper);
    return TableDataInfo.build(page);
  }
  //#endregion

  //#region 根据条件分页查询未分配用户角色列表

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param user 用户信息
   * @return 用户信息集合信息
   */
  @Override
  public TableDataInfo<SysUserVo> selectUnallocatedList(SysUserBo user, PageQuery pageQuery) {
    List<Long> userIds = CollUtil.newArrayList(0L); //userRoleMapper.selectUserIdsByRoleId(user.getRoleId());
    QueryWrapper<SysUser> wrapper = Wrappers.query();
    wrapper.eq("u.del_flag", UserConstants.USER_NORMAL)
//      .and(w -> w.ne("r.role_id", user.getRoleId()).or().isNull("r.role_id"))
      .notIn(CollUtil.isNotEmpty(userIds), "u.user_id", userIds)
      .like(StringUtils.isNotBlank(user.getUserName()), "u.user_name", user.getUserName())
      .like(StringUtils.isNotBlank(user.getPhoneNumber()), "u.phone_number", user.getPhoneNumber())
      .orderByAsc("u.user_id");
    Page<SysUserVo> page = baseMapper.selectUnallocatedList(pageQuery.build(), wrapper);
    return TableDataInfo.build(page);
  }
  //#endregion

  //#region 通过用户名查询用户

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  @Override
  public SysUserVo selectUserByUserName(String userName) {
    return baseMapper.selectUserByUserName(userName);
  }
  //#endregion

  //#region 通过手机号查询用户

  /**
   * 通过手机号查询用户
   *
   * @param phoneNumber 手机号
   * @return 用户对象信息
   */
  @Override
  public SysUserVo selectUserByPhoneNumber(String phoneNumber) {
    return baseMapper.selectUserByPhoneNumber(phoneNumber);
  }
  //#endregion

  //#region 通过用户ID查询用户

  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  @Override
  public SysUserVo selectUserById(Long userId) {
    return baseMapper.selectUserById(userId);
  }
  //#endregion

  //#region 查询用户所属角色组

  /**
   * 查询用户所属角色组
   *
   * @param userName 用户名
   * @return 结果
   */
  @Override
  public String selectUserRoleGroup(String userName) {
    List<SysRoleVo> list = roleMapper.selectRolesByUserName(userName);
    if (CollUtil.isEmpty(list)) {
      return StringUtils.EMPTY;
    }
    return StreamUtils.join(list, SysRoleVo::getRoleName);
  }
  //#endregion

  //#region 查询用户所属岗位组

  /**
   * 查询用户所属岗位组
   *
   * @param userName 用户名
   * @return 结果
   */
  @Override
  public String selectUserPostGroup(String userName) {
    List<SysPostVo> list = postMapper.selectPostsByUserName(userName);
    if (CollUtil.isEmpty(list)) {
      return StringUtils.EMPTY;
    }
    return StreamUtils.join(list, SysPostVo::getPostName);
  }
  //#endregion

  //#region 校验用户名称是否唯一

  /**
   * 校验用户名称是否唯一
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean checkUserNameUnique(SysUserBo user) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
      .eq(SysUser::getUserName, user.getUserName())
      .ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
    return !exist;
  }
  //#endregion

  /**
   * 校验手机号码是否唯一
   *
   * @param user 用户信息
   */
  @Override
  public boolean checkPhoneUnique(SysUserBo user) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
      .eq(SysUser::getPhoneNumber, user.getPhoneNumber())
      .ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
    return !exist;
  }

  /**
   * 校验email是否唯一
   *
   * @param user 用户信息
   */
  @Override
  public boolean checkEmailUnique(SysUserBo user) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysUser>()
      .eq(SysUser::getEmail, user.getEmail())
      .ne(ObjectUtil.isNotNull(user.getUserId()), SysUser::getUserId, user.getUserId()));
    return !exist;
  }

  /**
   * 校验用户是否允许操作
   *
   * @param userId 用户ID
   */
  @Override
  public void checkUserAllowed(Long userId) {
    if (ObjectUtil.isNotNull(userId)) {
      final SysUser sysUser = this.getById(userId);
      if (sysUser.isSuperAdmin()) {
        throw new ServiceException("不允许操作超级管理员用户");
      }
    }
  }

  /**
   * 校验用户是否有数据权限
   *
   * @param userId 用户id
   */
  @Override
  public void checkUserDataScope(Long userId) {
    if (ObjectUtil.isNull(userId)) {
      return;
    }
    if (LoginHelper.isSuperAdmin()) {
      return;
    }
    if (ObjectUtil.isNull(baseMapper.selectUserById(userId))) {
      throw new ServiceException("没有权限访问用户数据！");
    }
  }

  /**
   * 新增保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int insertUser(SysUserBo user) {
    SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
    // 新增用户信息
    int rows = baseMapper.insert(sysUser);
    user.setUserId(sysUser.getUserId());
    // 新增用户岗位关联
    insertUserPost(user, false);
    // 新增用户与角色管理
    insertUserRole(user, false);
    return rows;
  }

  /**
   * 注册用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public boolean registerUser(SysUserBo user, String tenantId) {
    user.setCreateBy(user.getUserId());
    user.setUpdateBy(user.getUserId());
    SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
    sysUser.setTenantId(tenantId);
    return baseMapper.insert(sysUser) > 0;
  }

  /**
   * 修改保存用户信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateUser(SysUserBo user) {
    // 新增用户与角色管理
    insertUserRole(user, true);
    // 新增用户与岗位管理
    insertUserPost(user, true);
    SysUser sysUser = MapstructUtils.convert(user, SysUser.class);
    // 防止错误更新后导致的数据误删除
    int flag = baseMapper.updateById(sysUser);
    if (flag < 1) {
      throw new ServiceException("修改用户" + user.getUserName() + "信息失败");
    }
    return flag;
  }

  /**
   * 用户授权角色
   *
   * @param userId  用户ID
   * @param roleIds 角色组
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void insertUserAuth(Long userId, Long[] roleIds) {
    insertUserRole(userId, roleIds, true);
  }

  /**
   * 修改用户状态
   *
   * @param userId 用户ID
   * @param enable 帐号状态
   * @return 结果
   */
  @Override
  public int updateUserStatus(Long userId, byte enable) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<SysUser>()
        .set(SysUser::getEnable, enable)
        .eq(SysUser::getUserId, userId));
  }

  /**
   * 修改用户基本信息
   *
   * @param user 用户信息
   * @return 结果
   */
  @Override
  public int updateUserProfile(SysUserBo user) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<SysUser>()
        .set(ObjectUtil.isNotNull(user.getNickName()), SysUser::getNickName, user.getNickName())
        .set(SysUser::getPhoneNumber, user.getPhoneNumber())
        .set(SysUser::getEmail, user.getEmail())
        .set(SysUser::getSex, user.getSex())
        .eq(SysUser::getUserId, user.getUserId()));
  }

  /**
   * 修改用户头像
   *
   * @param userId 用户ID
   * @param avatar 头像地址
   * @return 结果
   */
  @Override
  public boolean updateUserAvatar(Long userId, Long avatar) {
    return baseMapper.update(null,
      new LambdaUpdateWrapper<SysUser>()
        .set(SysUser::getAvatar, avatar)
        .eq(SysUser::getUserId, userId)) > 0;
  }

  /**
   * 重置用户密码
   *
   * @param userId           用户ID
   * @param passwordStrength 密码强度值
   * @param password         密码
   * @return 结果
   */
  @Override
  public int resetUserPwd(Long userId, double passwordStrength, String password) {
    SysUser user = this.getById(userId);

    // 更新profile中的密码
    LambdaUpdateWrapper<SysTenantProfile> tenantProfileLambdaUpdateWrapper = new LambdaUpdateWrapper<SysTenantProfile>()
      .set(SysTenantProfile::getPassword, password)
      .set(SysTenantProfile::getPasswordStrength, passwordStrength)
      .set(SysTenantProfile::getPasswordLastDate, DateUtil.date())
      .eq(SysTenantProfile::getPhoneNumber, user.getPhoneNumber());
    sysTenantProfileService.update(tenantProfileLambdaUpdateWrapper);

    // 更新用户表中的密码，多个账套同时更新，密码同步
    TenantHelper.enableIgnore();
    int result = baseMapper.update(null,
      new LambdaUpdateWrapper<SysUser>()
        .set(SysUser::getPassword, password)
        .set(SysUser::getPasswordStrength, passwordStrength)
        .set(SysUser::getPasswordLastDate, DateUtil.date())
        .eq(SysUser::getPhoneNumber, user.getPhoneNumber()));
    TenantHelper.disableIgnore();

    return result;
  }

  /**
   * 新增用户角色信息
   *
   * @param user  用户对象
   * @param clear 清除已存在的关联数据
   */
  private void insertUserRole(SysUserBo user, boolean clear) {
    this.insertUserRole(user.getUserId(), user.getRoleIds(), clear);
  }

  /**
   * 新增用户岗位信息
   *
   * @param user  用户对象
   * @param clear 清除已存在的关联数据
   */
  private void insertUserPost(SysUserBo user, boolean clear) {
    Long[] posts = user.getPostIds();
    if (ArrayUtil.isNotEmpty(posts)) {
      if (clear) {
        // 删除用户与岗位关联
        userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, user.getUserId()));
      }
      // 新增用户与岗位管理
      List<SysUserPost> list = StreamUtils.toList(List.of(posts), postId -> {
        SysUserPost up = new SysUserPost();
        up.setUserId(user.getUserId());
        up.setPostId(postId);
        return up;
      });
      userPostMapper.insertBatch(list);
    }
    // 清空缓存
    CacheUtils.evict(CacheNames.SYS_POST_LIST_BY_USER_ID, user.getUserId());
  }

  /**
   * 新增用户角色信息
   *
   * @param userId  用户ID
   * @param roleIds 角色组
   * @param clear   清除已存在的关联数据
   */
  private void insertUserRole(Long userId, Long[] roleIds, boolean clear) {
    if (ArrayUtil.isNotEmpty(roleIds)) {
      // 判断是否具有此角色的操作权限
      List<SysRoleVo> roles = roleMapper.selectRoleList(new LambdaQueryWrapper<>());
      if (CollUtil.isEmpty(roles)) {
        throw new ServiceException("没有权限访问角色的数据");
      }
      List<Long> roleList = StreamUtils.toList(roles, SysRoleVo::getRoleId);
      if (!LoginHelper.isSuperAdmin(userId)) {
        roleList.remove(UserConstants.SUPER_ADMIN_ID);
      }
      List<Long> canDoRoleList = StreamUtils.filter(List.of(roleIds), roleList::contains);
      if (CollUtil.isEmpty(canDoRoleList)) {
        throw new ServiceException("没有权限访问角色的数据");
      }
      if (clear) {
        // 删除用户与角色关联
        userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
      }
      // 新增用户与角色管理
      List<SysUserRole> list = StreamUtils.toList(canDoRoleList, roleId -> {
        SysUserRole ur = new SysUserRole();
        ur.setUserId(userId);
        ur.setRoleId(roleId);
        return ur;
      });
      userRoleMapper.insertBatch(list);
    }
    // 清空缓存
    CacheUtils.evict(CacheNames.SYS_ROLE_LIST_BY_USER_ID, userId);
  }

  /**
   * 通过用户ID删除用户
   *
   * @param userId 用户ID
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteUserById(Long userId) {
    // 删除用户与角色关联
    userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
    // 删除用户与岗位表
    userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().eq(SysUserPost::getUserId, userId));
    // 防止更新失败导致的数据删除
    int flag = baseMapper.deleteById(userId);
    if (flag < 1) {
      throw new ServiceException("删除用户失败!");
    }
    return flag;
  }

  /**
   * 批量删除用户信息
   *
   * @param userIds 需要删除的用户ID
   * @return 结果
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteUserByIds(Long[] userIds) {
    for (Long userId : userIds) {
      checkUserAllowed(userId);
      checkUserDataScope(userId);
    }
    List<Long> ids = List.of(userIds);
    // 删除用户与角色关联
    userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, ids));
    // 删除用户与岗位表
    userPostMapper.delete(new LambdaQueryWrapper<SysUserPost>().in(SysUserPost::getUserId, ids));
    // 防止更新失败导致的数据删除
    int flag = baseMapper.deleteBatchIds(ids);
    if (flag < 1) {
      throw new ServiceException("删除用户失败!");
    }
    return flag;
  }

  /**
   * 通过部门id查询当前部门所有用户
   *
   * @param deptId
   * @return
   */
  @Override
  public List<SysUserVo> selectUserListByDept(Long deptId) {
    LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery();
    lqw.eq(SysUser::getDeptId, deptId);
    lqw.orderByAsc(SysUser::getUserId);
    return baseMapper.selectVoList(lqw);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public R<Void> modifyPhoneNumber(ModifyPhoneNumberBody user) {
    String phoneNumber = user.getPhoneNumber();

    // 滑块验证码二次验证
    CaptchaVO captchaVO = new CaptchaVO();
    captchaVO.setCaptchaVerification(user.getCaptchaVerification());
    ResponseModel verification = captchaService.verification(captchaVO);
    if (!verification.isSuccess()) {
      throw new UserException("captcha.verification.expired");
    }

    // 验证码开关
    validateCaptcha(LoginHelper.getPhoneNumber(), user.getCode());
    SysTenantProfileBo profileBo = new SysTenantProfileBo();
    profileBo.setPhoneNumber(phoneNumber);

    // 判断手机号是否已经存在
    if (!sysTenantProfileService.checkPhoneUnique(profileBo)) {
      throw new UserException("user.modify.phoneNumber.exist.error", phoneNumber);
    }
    // 修改profile中的手机号
    boolean regFlag = sysTenantProfileService.modifyPhoneNumber(profileBo.getPhoneNumber());
    // 修改用户表中的手机号
    LambdaUpdateWrapper<SysUser> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    userLambdaUpdateWrapper
      .set(SysUser::getPhoneNumber, profileBo.getPhoneNumber())
      .set(SysUser::getPhoneVerified, EnableEnum.DISABLE.getId())
      .eq(SysUser::getUserId, LoginHelper.getUserId());
    regFlag = regFlag && this.update(userLambdaUpdateWrapper);

    if (!regFlag) {
      throw new UserException("user.modify.phoneNumber.error");
    }

    return R.ok(MessageUtils.message("user.modify.phoneNumber.success"));
  }

  @Override
  public R<Void> modifyEmail(ModifyEmailBody emailBody) {
    String email = emailBody.getEmail();

    // 滑块验证码二次验证
    CaptchaVO captchaVO = new CaptchaVO();
    captchaVO.setCaptchaVerification(emailBody.getCaptchaVerification());
    ResponseModel verification = captchaService.verification(captchaVO);
    if (!verification.isSuccess()) {
      throw new UserException("captcha.verification.expired");
    }

    // 验证码开关
    validateEmailCode(LoginHelper.getEmail(), emailBody.getCode());
    SysTenantProfileBo profileBo = new SysTenantProfileBo();
    profileBo.setEmail(email);

    // 判断手机号是否已经存在
    if (!sysTenantProfileService.checkEmailUnique(profileBo)) {
      throw new UserException("user.modify.email.exist.error", email);
    }
    // 修改profile中的手机号
    boolean regFlag = sysTenantProfileService.modifyEmail(profileBo.getEmail());
    // 修改用户表中的手机号
    LambdaUpdateWrapper<SysUser> userLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    userLambdaUpdateWrapper
      .set(SysUser::getEmail, profileBo.getEmail())
      .set(SysUser::getEmailVerified, EnableEnum.DISABLE.getId())
      .eq(SysUser::getUserId, LoginHelper.getUserId());
    regFlag = regFlag && this.update(userLambdaUpdateWrapper);

    if (!regFlag) {
      throw new UserException("user.modify.email.error");
    }

    return R.ok(MessageUtils.message("user.modify.email.success"));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean emailActivate(String email) {
    // 激活profile邮箱
    boolean regFlag = sysTenantProfileService.emailActivate(email);
    String sql = new SQL()
      .UPDATE("sys_user")
      .SET("email_verified={0}")
      .WHERE("email = {1}")
      .toString();
    regFlag = regFlag && SqlRunner.db().update(sql, EnableEnum.ENABLE.getId(), email);

    if (!regFlag) {
      throw new UserException("user.email.active.error");
    }

    return regFlag;
  }

  @Override
  public boolean phoneActivate(String phoneNumber) {
    // 激活profile邮箱
    boolean regFlag = sysTenantProfileService.phoneActivate(phoneNumber);
    String sql = new SQL()
      .UPDATE("sys_user")
      .SET("phone_verified={0}")
      .WHERE("phone_number = {1}")
      .toString();
    regFlag = regFlag && SqlRunner.db().update(sql, EnableEnum.ENABLE.getId(), phoneNumber);

    if (!regFlag) {
      throw new UserException("user.phone.active.error");
    }

    return regFlag;
  }


  /**
   * 校验验证码
   *
   * @param phoneNumber 用户名
   * @param code        验证码
   */
  public void validateCaptcha(String phoneNumber, String code) {
    String verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + phoneNumber;
    String captcha = RedisUtils.getCacheObject(verifyKey);
    RedisUtils.deleteObject(verifyKey);
    if (captcha == null) {
      throw new CaptchaExpireException();
    }
    if (!code.equalsIgnoreCase(captcha)) {
      throw new CaptchaException();
    }
  }

  /**
   * 校验邮箱验证码
   */
  private void validateEmailCode(String email, String emailCode) {
    String code = RedisUtils.getCacheObject(GlobalConstants.CAPTCHA_CODE_KEY + email);
    if (StringUtils.isBlank(code)) {
      throw new CaptchaExpireException();
    }
  }

  @Cacheable(cacheNames = CacheNames.SYS_USER_NAME, key = "#userId")
  @Override
  public String selectUserNameById(Long userId) {
    SysUser sysUser = baseMapper.selectOne(new LambdaQueryWrapper<SysUser>()
      .select(SysUser::getUserName).eq(SysUser::getUserId, userId));
    return ObjectUtil.isNull(sysUser) ? null : sysUser.getUserName();
  }

  @Cacheable(cacheNames = CacheNames.SYS_NICK_NAME, key = "#userId")
  @Override
  public String selectNickNameById(Long userId) {
    SysUser sysUser = baseMapper.selectOne(new LambdaQueryWrapper<SysUser>()
      .select(SysUser::getNickName).eq(SysUser::getUserId, userId));
    return ObjectUtil.isNull(sysUser) ? null : sysUser.getNickName();
  }

  @Cacheable(cacheNames = CacheNames.SYS_ROLE_NAMES, key = "#userId")
  @Override
  public String selectRoleNamesById(Long userId) {
    List<SysRoleVo> sysRoleVoList = roleMapper.selectRolePermissionByUserId(userId);

    return ObjectUtil.isNull(sysRoleVoList) ? null : sysRoleVoList.stream().map(SysRoleVo::getRoleName).collect(Collectors.joining(","));
  }

  /**
   * 根据条件分页查询用户列表
   *
   * @param map 用户信息
   * @return 用户信息集合信息
   */
  @Override
  public List<Map<String, Object>> getUserList(Map<String, Object> map) {

    Integer take = Optional.ofNullable(map).map(m -> Convert.toInt(m.get("take"))).orElse(200); // 查询top N，如果为空，默认200
    String name = Convert.toStr(map.get("name"));
    Long id = Convert.toLong(map.get("id"));
    String searchFields = Convert.toStr(map.get("searchFields"));
    Long storageUser = Convert.toLong(map.get("storageUser"));

    LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper
      .and(StringUtils.isNotEmpty(name),
        a -> a.like(StringUtils.isNotEmpty(name), SysUser::getNickName, name)
      ); // 关键词对编号和名称模糊查询
    if (ObjectUtil.isNotNull(storageUser)) {
      queryWrapper.eq(SysUser::getStorageUser, EnableEnum.ENABLE.getId()); // 是否可用
    }
    if (ObjectUtil.isNotNull(id)) {
      queryWrapper.eq(SysUser::getUserId, id); // id
    }

    try {
      List<String> fields = Arrays.asList("userId", "nickName", "deptId", "deptName"); // 查询默认字段
      // 自定义查询字段
      if (StringUtils.isNotEmpty(searchFields)) {
        fields = List.of(StringUtils.split(searchFields, ",")); // 查询指定字段
      }
      if ("*".equals(searchFields)) {
        fields.clear(); // 清空，查询所有字段
      }

      // 自定义查询字段
      if (fields.size() > 0) {
        List<String> finalFields = fields;
        queryWrapper.select(SysUser.class, s -> finalFields.contains(s.getProperty()));
      }
      queryWrapper.orderByAsc(SysUser::getUserId); // 排序
      queryWrapper.last("limit " + take); // top N

      var dataList = this.baseMapper.selectMaps(queryWrapper);
      return dataList;
    } catch (Exception error) {
      var msg = "异常错误信息：" + error.getCause();
      throw new ServiceException(msg);
    }
  }

  /**
   * 返回用户角色ID
   *
   * @param userId 用户ID
   * @return 发挥角色ID集合
   */
  @Cacheable(cacheNames = CacheNames.SYS_ROLE_LIST_BY_USER_ID, key = "#userId")
  @Override
  public List<Long> selectRoleListByUserId(long userId) {
    var data = CacheUtils.get(CacheNames.SYS_ROLE_LIST_BY_USER_ID, userId);
    if (ObjectUtil.isNotNull(data)) {
      return Convert.toList(Long.class, data);
    } else {
      List<Long> roleIdList = roleMapper.selectRoleListByUserId(userId);
      CacheUtils.put(CacheNames.SYS_ROLE_LIST_BY_USER_ID, userId, roleIdList);
      return roleIdList;
    }
  }

  /**
   * 返回用户岗位ID
   *
   * @param userId 用户ID
   * @return 发挥角色ID集合
   */
  @Cacheable(cacheNames = CacheNames.SYS_POST_LIST_BY_USER_ID, key = "#userId")
  @Override
  public List<Long> selectPostListByUserId(long userId) {
    var data = CacheUtils.get(CacheNames.SYS_POST_LIST_BY_USER_ID, userId);
    if (ObjectUtil.isNotNull(data)) {
      return Convert.toList(Long.class, data);
    } else {
      List<Long> postIdList = sysPostService.selectPostListByUserId(userId);
      CacheUtils.put(CacheNames.SYS_POST_LIST_BY_USER_ID, userId, postIdList);
      return postIdList;
    }
  }

  @Override
  public R<Void> open(Map<String, Object> map) {
    try {
      List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
      for (Long id : ids) {
        SysUser sysUser = this.getById(id);
        if (ObjectUtil.isNull(sysUser)) {
          throw new ServiceException("用户不存在！");
        }

        if (!SysUserEnum.STOP.getId().equals(sysUser.getEnable()) && !SysUserEnum.AUDIT.getId().equals(sysUser.getEnable())) {
          throw new ServiceException("只有未审核或停止的单据才可以启用！");
        }
        String code = DBUtils.getCodeRegular(MenuEnum.MENU_4, LoginHelper.getLoginUser().getTenantId());

        // YY2025022701
        String secondLevelMerchantCode = "1280036" + code.substring(4, 12);

        //#region 扩展字段
        Map<String, Object> expandFields = new HashMap<>();
        expandFields.put("secondLevelMerchantCode", secondLevelMerchantCode); //
        sysUser.setExpandFields(expandFields); //
        sysUser.setEnable(SysUserEnum.OPEN.getId());
        sysUser.setUserCode(code);
        this.saveOrUpdate(sysUser);

        // YY2025022701
        //#region 发送邮件 ：邮箱账号+标题+内容
        String html = this.generateHtmlContent(sysUser);
        if (ObjectUtil.isNotNull(html)) {
          MailUtils.sendHtml(sysUser.getEmail(), "平台注册服务通知", html);
        }
        //#endregion

      }
      return R.ok("启用成功！");
    } catch (Exception exception) {

      return R.fail(exception.getMessage());
    }
  }
  //#endregion

  //#region stop
  @Override
  public R<Void> stop(Map<String, Object> map) {
    try {
      List<Long> ids = StreamUtils.toList(Convert.toList(map.get("ids")), Convert::toLong);
      for (Long id : ids) {
        SysUser sysUser = this.getById(id);
        if (!SysUserEnum.OPEN.getId().equals(sysUser.getEnable())) {
          throw new ServiceException("只有启用的单据才可以停止！");
        }

        //修改数据
        LambdaUpdateWrapper<SysUser> lambda = new LambdaUpdateWrapper<>();
        lambda.set(SysUser::getEnable, SysUserEnum.STOP.getId())
          .eq(SysUser::getUserId, sysUser.getUserId());
        this.update(lambda);//提交
      }
      return R.ok("停用成功！");
    } catch (Exception exception) {

      return R.fail(exception.getMessage());
    }
  }
  //#endregion


  //#region 注册用户

  /**
   * 注册用户
   */
  @SaIgnore // 忽略接口鉴权
  @Override
  public R<Void> register(SysUserBo userBo) {
    try {
      //#region 判空判重
      if (ObjectUtil.isNull(userBo)) {
        return R.fail("用户信息为空！请检查");
      }
      LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(SysUser::getUserName, userBo.getNickName());
//        .eq(SysUser::getPhoneNumber, userBo.getPhoneNumber());
      SysUser userInfo = this.getOnly(queryWrapper);
      if (ObjectUtil.isNotNull(userInfo)) {
        return R.fail("当前商家" + userBo.getNickName() + "已存在！请检查");
      }
      //#endregion

      TenantHelper.enableIgnore(); // 开启忽略租户
      // 插入用户信息
      SysUser sysUser = new SysUser();
      BeanUtil.copyProperties(userBo, sysUser); // 复制前端对象到用户对象
      sysUser.setUserName(userBo.getNickName());
      sysUser.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
      sysUser.setEnable(EnableEnum.DISABLE.getId());
      sysUser.setUserType(UserTypeEnum.SYS_USER.getUserType());
      // 默认初始密码
      String pwd = Constants.DEFAULT_PASSWORD;
      pwd = BCrypt.hashpw(pwd);
      sysUser.setPassword(pwd);
      this.save(sysUser);

      //#region 插入用户角色信息
      String roleName = rattanConfig.getRegDefaultRoleName(); // 默认角色名称
      SysUserRole userRole = new SysUserRole();
      // 查询角色名称
      SysRole sysRole = sysRoleService.getByName(roleName);
      if (ObjectUtil.isNotNull(sysRole)) {
        userRole.setUserId(sysUser.getUserId());
        userRole.setRoleId(sysRole.getRoleId());
        sysUserRoleService.save(userRole);
      } else {
        // 新建角色信息
        SysRole role = new SysRole();
        role.setRoleName(roleName);
        role.setEnable(EnableEnum.ENABLE.getId());
        sysRoleService.save(role);
        // 更新用户角色信息
        userRole.setUserId(sysUser.getUserId());
        userRole.setRoleId(role.getRoleId());
        sysUserRoleService.save(userRole);
      }
      //#endregion

      // 插入租户信息
      SysTenantProfile tenantProfile = new SysTenantProfile();
      BeanUtil.copyProperties(sysUser, tenantProfile); // 复制用户对象到租户对象
      sysTenantProfileService.save(tenantProfile);
      TenantHelper.disableIgnore(); // 关闭忽略租户

      //#region 发送邮件 ：邮箱账号+标题+内容
//      String html = this.generateHtmlContent(sysUser);
//      if (ObjectUtil.isNotNull(html)) {
//        MailUtils.sendHtml(sysUser.getEmail(), "平台注册服务通知", html);
//      }
      //#endregion

      return R.ok("商家注册成功！");
    } catch (Exception exception) {
      return R.fail(exception.getMessage());
    }
  }
  //#endregion

  //#region 生成邮件内容

  /**
   * 生成邮件内容
   *
   * @param sysUser
   * @return
   */
  @Override
  public String generateHtmlContent(SysUser sysUser) {
    StringBuilder sb = new StringBuilder();
    sb.append("<!DOCTYPE html>\n");
    sb.append("<html lang=\"zh-CN\">\n");
    sb.append("<head>\n");
    sb.append("<meta charset=\"UTF-8\">\n");
    sb.append("<title>欢迎成为测试平台用户</title>\n");
    sb.append("</head>\n");
    sb.append("<body>\n");

    sb.append("<div class=\"header\">\n");
    sb.append("<h1>恭喜您已成为测试平台用户！</h1>\n");
    sb.append("</div>\n");

    Object secondLevelMerchantCode = sysUser.getExpandFields().get("secondLevelMerchantCode");

    sb.append("<div class=\"content\">\n");
    sb.append("<p>您的签约信息如下：</p>\n");
    sb.append("<ul>\n");
    sb.append("<li>商户名：" + sysUser.getNickName() + "</li>\n");
    sb.append("<li>二级商户代码：" + secondLevelMerchantCode + "</li>\n");
    sb.append("</ul>\n");

    sb.append("<p>您现在可以登录测试平台：</p>\n");
    sb.append("<ul>\n");
    sb.append("<li>登录地址：http://baofoo.yunlianbao.cn/</li>\n");
    sb.append("<li>操作员用户名：" + sysUser.getPhoneNumber() + "</li>\n");
    sb.append("<li>登录密码：000000</li>\n");
    sb.append("</ul>\n");

    sb.append("<p>登录测试平台后，系统将会强制性修改登录密码！</p>\n");
    sb.append("<p>修改登录密码之后，请重新登录测试平台开始使用!</p>\n");
    sb.append("</div>\n");

    sb.append("<div class=\"warning\">\n");
    sb.append("<strong>特别提示：</strong>\n");
    sb.append("用户注册成功后即成为平台的注册用户，可以使用平台提供的服务。平台旨在提供一个模拟环境，用于测试订单支付交易流程。通过本平台，可以模拟形成真实支付订单。本平台发出的测试订单将被真实推送至您所指定的付款银行，形成真实的支付订单。用户可以忽略该真实订单，也可进行实际支付。因而用户须在实际支付前，在付款银行的操作界面仔细核对支付订单信息，以确保所有细节准确无误后进行支付。任何支付测试的结果均以付款银行的记录和反馈为最终依据。本平台不承担因注册用户对平台使用误解或订单信息填写错误而造成的用户财务损失。\n");
    sb.append("</div>\n");

    sb.append("<div class=\"footer\">\n");
    sb.append("<p>本邮件由测试平台系统自动发出，系统不接受回信，请勿直接回复。</p>\n");
    sb.append("<p>如有任何疑问，有任何问题可致电：136xxxxxxxx，或者发至邮箱：{邮箱地址}</p>\n");
    sb.append("<p>祝您测试顺利！</p>\n");
    // 格式化日期
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = sdf1.format(new Date());
    sb.append("<p>测试平台<br>" + formattedDate + "</p>\n");
    sb.append("</div>\n");
    sb.append("</body>\n");
    sb.append("</html>\n");

    return sb.toString();
  }

  @Override
  public R<Map<String, Object>> apiAdd(SysUserBo bo) {
    SysUser sysUser = this.getByName(bo.getUserName());

    if (ObjectUtil.isNotNull(sysUser)) {
      BeanUtil.copyProperties(bo, sysUser, new CopyOptions().setIgnoreProperties("userId"));
      this.saveOrUpdate(sysUser);

      Map<String, Object> result = new HashMap<>();
      result.put("userName", sysUser.getUserName());
      return R.ok("用户信息更新成功", result);
    }

    sysUser = new SysUser();
    BeanUtil.copyProperties(bo, sysUser, new CopyOptions().setIgnoreProperties("userId"));
    this.save(sysUser);

    Map<String, Object> result = new HashMap<>();
    result.put("userName", sysUser.getUserName());
    return R.ok("用户信息添加成功", result);

  }

  @Override
  public SysUser getByName(String userName) {
    LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(SysUser::getUserName, userName);
    return this.getOnly(lambdaQueryWrapper);
  }
}

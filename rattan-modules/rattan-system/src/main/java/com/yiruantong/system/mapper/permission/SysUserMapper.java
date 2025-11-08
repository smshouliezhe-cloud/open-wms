package com.yiruantong.system.mapper.permission;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.permission.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import com.yiruantong.common.mybatis.annotation.DataColumn;
import com.yiruantong.common.mybatis.annotation.DataPermission;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author YiRuanTong
 */
public interface SysUserMapper extends BaseMapperPlus<SysUser, SysUserVo> {

  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "u.user_id")
  })
  Page<SysUserVo> selectPageUserList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

  /**
   * 根据条件分页查询用户列表
   *
   * @param queryWrapper 查询条件
   * @return 用户信息集合信息
   */
  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "u.user_id")
  })
  List<SysUserVo> selectUserList(@Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

  /**
   * 根据条件分页查询已配用户角色列表
   *
   * @param queryWrapper 查询条件
   * @return 用户信息集合信息
   */
  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "u.user_id")
  })
  Page<SysUserVo> selectAllocatedList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

  /**
   * 根据条件分页查询未分配用户角色列表
   *
   * @param queryWrapper 查询条件
   * @return 用户信息集合信息
   */
  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "u.user_id")
  })
  Page<SysUserVo> selectUnallocatedList(@Param("page") Page<SysUser> page, @Param(Constants.WRAPPER) Wrapper<SysUser> queryWrapper);

  /**
   * 通过用户名查询用户
   *
   * @param userName 用户名
   * @return 用户对象信息
   */
  SysUserVo selectUserByUserName(String userName);

  /**
   * 通过手机号查询用户
   *
   * @param phoneNumber 手机号
   * @return 用户对象信息
   */
  SysUserVo selectUserByPhoneNumber(String phoneNumber);

  /**
   * 通过邮箱查询用户
   *
   * @param email 邮箱
   * @return 用户对象信息
   */
  SysUserVo selectUserByEmail(String email);

  /**
   * 通过用户名查询用户(不走租户插件)
   *
   * @param userName 用户名
   * @param tenantId 租户id
   * @return 用户对象信息
   */
  @InterceptorIgnore(tenantLine = "true")
  SysUserVo selectTenantUserByUserName(@Param("userName") String userName, @Param("tenantId") String tenantId);

  /**
   * 通过手机号查询用户(不走租户插件)
   *
   * @param phoneNumber 手机号
   * @param tenantId    租户id
   * @return 用户对象信息
   */
  @InterceptorIgnore(tenantLine = "true")
  SysUserVo selectTenantUserByPhoneNumber(@Param("phoneNumber") String phoneNumber, @Param("tenantId") String tenantId);

  /**
   * 通过邮箱查询用户(不走租户插件)
   *
   * @param email    邮箱
   * @param tenantId 租户id
   * @return 用户对象信息
   */
  @InterceptorIgnore(tenantLine = "true")
  SysUserVo selectTenantUserByEmail(@Param("email") String email, @Param("tenantId") String tenantId);


  /**
   * 通过用户ID查询用户
   *
   * @param userId 用户ID
   * @return 用户对象信息
   */
  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "u.user_id")
  })
  SysUserVo selectUserById(Long userId);

  @Override
  @DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
  })
  int update(@Param(Constants.ENTITY) SysUser user, @Param(Constants.WRAPPER) Wrapper<SysUser> updateWrapper);

  @Override
  @DataPermission({
    @DataColumn(key = "deptName", value = "dept_id"),
    @DataColumn(key = "userName", value = "user_id")
  })
  int updateById(@Param(Constants.ENTITY) SysUser user);
}

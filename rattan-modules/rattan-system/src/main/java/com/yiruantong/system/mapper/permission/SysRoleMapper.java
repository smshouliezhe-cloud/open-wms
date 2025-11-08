package com.yiruantong.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yiruantong.system.domain.permission.SysRole;
import com.yiruantong.system.domain.permission.vo.SysRoleVo;
import com.yiruantong.common.mybatis.annotation.DataColumn;
import com.yiruantong.common.mybatis.annotation.DataPermission;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色表 数据层
 *
 * @author YiRuanTong
 */
public interface SysRoleMapper extends BaseMapperPlus<SysRole, SysRoleVo> {

  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "r.create_by")
  })
  Page<SysRoleVo> selectPageRoleList(@Param("page") Page<SysRole> page, @Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

  /**
   * 根据条件分页查询角色数据
   *
   * @param queryWrapper 查询条件
   * @return 角色数据集合信息
   */
  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "r.create_by")
  })
  List<SysRoleVo> selectRoleList(@Param(Constants.WRAPPER) Wrapper<SysRole> queryWrapper);

  @DataPermission({
    @DataColumn(key = "deptName", value = "d.dept_id"),
    @DataColumn(key = "userName", value = "r.create_by")
  })
  SysRoleVo selectRoleById(Long roleId);

  /**
   * 根据用户ID查询角色
   *
   * @param userId 用户ID
   * @return 角色列表
   */
  List<SysRoleVo> selectRolePermissionByUserId(Long userId);


  /**
   * 根据用户ID获取角色选择框列表
   *
   * @param userId 用户ID
   * @return 选中角色ID列表
   */
  List<Long> selectRoleListByUserId(Long userId);

  /**
   * 根据用户ID查询角色
   *
   * @param userName 用户名
   * @return 角色列表
   */
  List<SysRoleVo> selectRolesByUserName(String userName);

}

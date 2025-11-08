package com.yiruantong.system.service.permission;

import cn.hutool.core.lang.tree.Tree;
import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.system.domain.permission.bo.SysDeptBo;
import com.yiruantong.system.domain.permission.vo.SysDeptVo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;

/**
 * 部门Service接口
 *
 * @author YiRuanTong
 * @date 2023-09-30
 */
public interface ISysDeptService extends IServicePlus<SysDept, SysDeptVo, SysDeptBo> {
  /**
   * 查询部门管理数据
   *
   * @param dept 部门信息
   * @return 部门信息集合
   */
  List<SysDeptVo> selectDeptList(SysDeptBo dept);

  /**
   * 查询部门树结构信息
   *
   * @param dept 部门信息
   * @return 部门树信息集合
   */
  List<Tree<Long>> selectDeptTreeList(SysDeptBo dept);

  /**
   * 构建前端所需要下拉树结构
   *
   * @param depts 部门列表
   * @return 下拉树结构列表
   */
  List<Tree<Long>> buildDeptTreeSelect(List<SysDeptVo> depts);

  /**
   * 根据角色ID查询部门树信息
   *
   * @param roleId 角色ID
   * @return 选中部门列表
   */
  List<Long> selectDeptListByRoleId(Long roleId);

  /**
   * 根据部门ID查询信息
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  SysDeptVo selectDeptById(Long deptId);

  /**
   * 根据ID查询所有子部门数（正常状态）
   *
   * @param deptId 部门ID
   * @return 子部门数
   */
  long selectNormalChildrenDeptById(Long deptId);

  /**
   * 是否存在部门子节点
   *
   * @param deptId 部门ID
   * @return 结果
   */
  boolean hasChildByDeptId(Long deptId);

  /**
   * 查询部门是否存在用户
   *
   * @param deptId 部门ID
   * @return 结果 true 存在 false 不存在
   */
  boolean checkDeptExistUser(Long deptId);

  /**
   * 校验部门名称是否唯一
   *
   * @param dept 部门信息
   * @return 结果
   */
  boolean checkDeptNameUnique(SysDeptBo dept);

  /**
   * 校验部门是否有数据权限
   *
   * @param deptId 部门id
   */
  void checkDeptDataScope(Long deptId);

  /**
   * 新增保存部门信息
   *
   * @param bo 部门信息
   * @return 结果
   */
  int insertDept(SysDeptBo bo);

  /**
   * 修改保存部门信息
   *
   * @param bo 部门信息
   * @return 结果
   */
  int updateDept(SysDeptBo bo);

  /**
   * 删除部门管理信息
   *
   * @param deptId 部门ID
   * @return 结果
   */
  int deleteDeptById(Long deptId);

  /**
   * 查询部门的父级部门
   *
   * @param deptId 查询条件
   * @return 返回查询列表数据
   */
  SysDept getDeptInfo(Long deptId);

  /**
   * 根据部门名称查询部门信息
   *
   * @param deptName 查询条件
   * @return 返回查询列表数据
   */
  SysDept getByName(String deptName);
}

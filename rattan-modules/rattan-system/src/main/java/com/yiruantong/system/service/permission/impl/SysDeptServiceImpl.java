package com.yiruantong.system.service.permission.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.system.domain.permission.SysRole;
import com.yiruantong.system.domain.permission.SysUser;
import com.yiruantong.system.domain.permission.bo.SysDeptBo;
import com.yiruantong.system.domain.permission.vo.SysDeptVo;
import com.yiruantong.system.mapper.permission.SysDeptMapper;
import com.yiruantong.system.mapper.permission.SysRoleMapper;
import com.yiruantong.system.mapper.permission.SysUserMapper;
import com.yiruantong.system.service.permission.ISysDeptService;
import com.yiruantong.system.service.permission.ISysUserService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.CacheNames;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.service.DeptService;
import com.yiruantong.common.core.utils.MapstructUtils;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.core.utils.TreeBuildUtils;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.mybatis.helper.DataBaseHelper;
import com.yiruantong.common.redis.utils.CacheUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 部门Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-09-30
 */
@RequiredArgsConstructor
@Service
public class SysDeptServiceImpl extends ServiceImplPlus<SysDeptMapper, SysDept, SysDeptVo, SysDeptBo> implements ISysDeptService, DeptService {
  private final SysDeptMapper baseMapper;
  private final SysRoleMapper roleMapper;
  private final SysUserMapper userMapper;
  private final ISysUserService sysUserService;


  /**
   * 分页查询前事件
   *
   * @param pageQuery 加载参数
   */
  @Override
  public void beforePageQuery(PageQuery pageQuery) {
    SysDeptPostServiceImpl.treeQuery(pageQuery, baseMapper);
  }

  /**
   * 查询部门管理数据
   *
   * @param dept 部门信息
   * @return 部门信息集合
   */
  @Override
  public List<SysDeptVo> selectDeptList(SysDeptBo dept) {
    LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(dept);
    return baseMapper.selectDeptList(lqw);
  }

  /**
   * 查询部门树结构信息
   *
   * @param bo 部门信息
   * @return 部门树信息集合
   */
  @Override
  public List<Tree<Long>> selectDeptTreeList(SysDeptBo bo) {
    // 只查询未禁用部门
    bo.setEnable((int) UserConstants.DEPT_NORMAL);
    LambdaQueryWrapper<SysDept> lqw = buildQueryWrapper(bo);
    List<SysDeptVo> depts = baseMapper.selectDeptList(lqw);
    return buildDeptTreeSelect(depts);
  }

  private LambdaQueryWrapper<SysDept> buildQueryWrapper(SysDeptBo bo) {
    LambdaQueryWrapper<SysDept> lqw = Wrappers.lambdaQuery();
    lqw.eq(SysDept::getDelFlag, UserConstants.USER_NORMAL);
    lqw.eq(ObjectUtil.isNotNull(bo.getDeptId()), SysDept::getDeptId, bo.getDeptId());
    lqw.eq(ObjectUtil.isNotNull(bo.getParentId()), SysDept::getParentId, bo.getParentId());
    lqw.like(StringUtils.isNotBlank(bo.getDeptName()), SysDept::getDeptName, bo.getDeptName());
    lqw.eq(ObjectUtil.isNotNull(bo.getEnable()), SysDept::getEnable, bo.getEnable());
    lqw.orderByAsc(SysDept::getDeptId);
    lqw.orderByAsc(SysDept::getParentId);
    lqw.orderByAsc(SysDept::getOrderNum);
    return lqw;
  }

  /**
   * 构建前端所需要下拉树结构
   *
   * @param depts 部门列表
   * @return 下拉树结构列表
   */
  @Override
  public List<Tree<Long>> buildDeptTreeSelect(List<SysDeptVo> depts) {
    if (CollUtil.isEmpty(depts)) {
      return CollUtil.newArrayList();
    }
    return TreeBuildUtils.build(depts, (dept, tree) ->
      tree.setId(dept.getDeptId())
        .setParentId(dept.getParentId())
        .setName(dept.getDeptName())
        .setWeight(dept.getOrderNum()));
  }

  /**
   * 根据角色ID查询部门树信息
   *
   * @param roleId 角色ID
   * @return 选中部门列表
   */
  @Override
  public List<Long> selectDeptListByRoleId(Long roleId) {
    SysRole role = roleMapper.selectById(roleId);
    return baseMapper.selectDeptListByRoleId(roleId, role.getDeptCheckStrictly());
  }

  /**
   * 根据部门ID查询信息
   *
   * @param deptId 部门ID
   * @return 部门信息
   */
  @Cacheable(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
  @Override
  public SysDeptVo selectDeptById(Long deptId) {
    SysDeptVo dept = baseMapper.selectVoById(deptId);
    if (ObjectUtil.isNull(dept)) {
      return null;
    }
    SysDeptVo parentDept = baseMapper.selectVoOne(new LambdaQueryWrapper<SysDept>().select(SysDept::getDeptName).eq(SysDept::getDeptId, dept.getParentId()));
    dept.setFullDeptName(ObjectUtil.isNotNull(parentDept) ? parentDept.getDeptName() : null);
    return dept;
  }

  /**
   * 通过部门ID查询部门名称
   *
   * @param deptIds 部门ID串逗号分隔
   * @return 部门名称串逗号分隔
   */
  @Override
  public String selectDeptNameByIds(String deptIds) {
    List<String> list = new ArrayList<>();
    for (Long id : StringUtils.splitTo(deptIds, Convert::toLong)) {
      SysDeptVo vo = SpringUtils.getAopProxy(this).selectDeptById(id);
      if (ObjectUtil.isNotNull(vo)) {
        list.add(vo.getDeptName());
      }
    }
    return String.join(StringUtils.SEPARATOR_COMMA, list);
  }

  /**
   * 根据ID查询所有子部门数（正常状态）
   *
   * @param deptId 部门ID
   * @return 子部门数
   */
  @Override
  public long selectNormalChildrenDeptById(Long deptId) {
    return baseMapper.selectCount(new LambdaQueryWrapper<SysDept>()
      .eq(SysDept::getEnable, UserConstants.DEPT_NORMAL)
      .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
  }

  /**
   * 是否存在子节点
   *
   * @param deptId 部门ID
   * @return 结果
   */
  @Override
  public boolean hasChildByDeptId(Long deptId) {
    return baseMapper.exists(new LambdaQueryWrapper<SysDept>()
      .eq(SysDept::getParentId, deptId));
  }

  /**
   * 查询部门是否存在用户
   *
   * @param deptId 部门ID
   * @return 结果 true 存在 false 不存在
   */
  @Override
  public boolean checkDeptExistUser(Long deptId) {
    return userMapper.exists(new LambdaQueryWrapper<SysUser>()
      .eq(SysUser::getDeptId, deptId));
  }

  /**
   * 校验部门名称是否唯一
   *
   * @param dept 部门信息
   * @return 结果
   */
  @Override
  public boolean checkDeptNameUnique(SysDeptBo dept) {
    boolean exist = baseMapper.exists(new LambdaQueryWrapper<SysDept>()
      .eq(SysDept::getDeptName, dept.getDeptName())
      .eq(SysDept::getParentId, dept.getParentId())
      .ne(ObjectUtil.isNotNull(dept.getDeptId()), SysDept::getDeptId, dept.getDeptId()));
    return !exist;
  }

  /**
   * 校验部门是否有数据权限
   *
   * @param deptId 部门id
   */
  @Override
  public void checkDeptDataScope(Long deptId) {
    if (ObjectUtil.isNull(deptId)) {
      return;
    }
    if (LoginHelper.isSuperAdmin()) {
      return;
    }
    SysDeptVo dept = baseMapper.selectDeptById(deptId);
    if (ObjectUtil.isNull(dept)) {
      throw new ServiceException("没有权限访问部门数据！");
    }
  }

  /**
   * 新增保存部门信息
   *
   * @param bo 部门信息
   * @return 结果
   */
  @Override
  public int insertDept(SysDeptBo bo) {
    SysDept info = baseMapper.selectById(bo.getParentId());
    // 如果父节点不为正常状态,则不允许新增子节点
    if (UserConstants.DEPT_NORMAL == info.getEnable()) {
      throw new ServiceException("部门停用，不允许新增");
    }
    SysDept dept = MapstructUtils.convert(bo, SysDept.class);
    dept.setAncestors(info.getAncestors() + StringUtils.SEPARATOR_COMMA + dept.getParentId());
    return baseMapper.insert(dept);
  }

  /**
   * 修改保存部门信息
   *
   * @param bo 部门信息
   * @return 结果
   */
  @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#bo.deptId")
  @Override
  public int updateDept(SysDeptBo bo) {
    SysDept dept = MapstructUtils.convert(bo, SysDept.class);
    SysDept oldDept = baseMapper.selectById(dept.getDeptId());
    if (!oldDept.getParentId().equals(dept.getParentId())) {
      // 如果是新父部门 则校验是否具有新父部门权限 避免越权
      this.checkDeptDataScope(dept.getParentId());
      SysDept newParentDept = baseMapper.selectById(dept.getParentId());
      if (ObjectUtil.isNotNull(newParentDept) && ObjectUtil.isNotNull(oldDept)) {
        String newAncestors = newParentDept.getAncestors() + StringUtils.SEPARATOR_COMMA + newParentDept.getDeptId();
        String oldAncestors = oldDept.getAncestors();
        dept.setAncestors(newAncestors);
        updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
      }
    }
    int result = baseMapper.updateById(dept);
    if (UserConstants.DEPT_NORMAL == dept.getEnable() && StringUtils.isNotEmpty(dept.getAncestors())
      && !StringUtils.equals(String.valueOf(UserConstants.DEPT_NORMAL), dept.getAncestors())) {
      // 如果该部门是启用状态，则启用该部门的所有上级部门
      updateParentDeptStatusNormal(dept);
    }
    return result;
  }

  /**
   * 修改该部门的父级部门状态
   *
   * @param dept 当前部门
   */
  private void updateParentDeptStatusNormal(SysDept dept) {
    String ancestors = dept.getAncestors();
    Long[] deptIds = Convert.toLongArray(ancestors);
    baseMapper.update(null, new LambdaUpdateWrapper<SysDept>()
      .set(SysDept::getEnable, UserConstants.DEPT_NORMAL)
      .in(SysDept::getDeptId, Arrays.asList(deptIds)));
  }

  /**
   * 修改子元素关系
   *
   * @param deptId       被修改的部门ID
   * @param newAncestors 新的父ID集合
   * @param oldAncestors 旧的父ID集合
   */
  private void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
    List<SysDept> children = baseMapper.selectList(new LambdaQueryWrapper<SysDept>()
      .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
    List<SysDept> list = new ArrayList<>();
    for (SysDept child : children) {
      SysDept dept = new SysDept();
      dept.setDeptId(child.getDeptId());
      dept.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
      list.add(dept);
    }
    if (CollUtil.isNotEmpty(list)) {
      if (baseMapper.updateBatchById(list)) {
        list.forEach(dept -> CacheUtils.evict(CacheNames.SYS_DEPT, dept.getDeptId()));
      }
    }
  }

  /**
   * 删除部门管理信息
   *
   * @param deptId 部门ID
   * @return 结果
   */
  @CacheEvict(cacheNames = CacheNames.SYS_DEPT, key = "#deptId")
  @Override
  public int deleteDeptById(Long deptId) {
    return baseMapper.deleteById(deptId);
  }

  //#region 查询部门的父级部门
  @Override
  public SysDept getDeptInfo(Long deptId) {
    LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
    sysDeptLambdaQueryWrapper.eq(SysDept::getDeptId, deptId);

    return this.getOne(sysDeptLambdaQueryWrapper);
  }
  //#endregion

  //#region 查询部门的父级部门
  @Override
  public SysDept getByName(String deptName) {
    LambdaQueryWrapper<SysDept> sysDeptLambdaQueryWrapper = new LambdaQueryWrapper<>();
    sysDeptLambdaQueryWrapper.eq(SysDept::getDeptName, deptName);

    return this.getOnly(sysDeptLambdaQueryWrapper);
  }
  //#endregion

  /**
   * 删除前事件
   *
   * @param Ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public int deleteByIds(Long[] Ids) {
    for (Long id : Ids) {
      // 查询当前部门信息
      SysDept sysDept = this.getById(id);
      // 如果部门不存在，跳过本次循环
      if (ObjectUtil.isNull(sysDept)) {
        continue;
      }

      // 查询当前部门下的用户数据
      LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(SysUser::getDeptName, sysDept.getDeptName());
      var coreList = sysUserService.selectList(lambdaQueryWrapper);
      if (!coreList.isEmpty()) {
        throw new ServiceException("当前部门【" + sysDept.getDeptName() + "】下存在用户，不允许删除！");
      }
    }
    // 执行删除 - 调用父类的批量删除方法
    return baseMapper.deleteBatchIds(Arrays.asList(Ids));
  }


}

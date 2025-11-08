package com.yiruantong.system.mapper.permission;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.system.domain.permission.vo.SysDeptVo;
import org.apache.ibatis.annotations.Param;
import com.yiruantong.common.mybatis.annotation.DataColumn;
import com.yiruantong.common.mybatis.annotation.DataPermission;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 部门Mapper接口
 *
 * @author YiRuanTong
 * @date 2023-09-30
 */
public interface SysDeptMapper extends BaseMapperPlus<SysDept, SysDeptVo> {
  /**
   * 查询部门管理数据
   *
   * @param queryWrapper 查询条件
   * @return 部门信息集合
   */
  @DataPermission({
    @DataColumn(key = "deptName", value = "dept_id")
  })
  List<SysDeptVo> selectDeptList(@Param(Constants.WRAPPER) Wrapper<SysDept> queryWrapper);

  @DataPermission({
    @DataColumn(key = "deptName", value = "dept_id")
  })
  SysDeptVo selectDeptById(Long deptId);

  /**
   * 根据角色ID查询部门树信息
   *
   * @param roleId            角色ID
   * @param deptCheckStrictly 部门树选择项是否关联显示
   * @return 选中部门列表
   */
  List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId, @Param("deptCheckStrictly") Byte deptCheckStrictly);

  /**
   * 根据ID获取所有子孙ID，包含自己
   *
   * @param deptId 部门ID
   * @return 所有子孙ID，包含自己
   */
  String getChildrenId(@Param("deptId") Long deptId);
}

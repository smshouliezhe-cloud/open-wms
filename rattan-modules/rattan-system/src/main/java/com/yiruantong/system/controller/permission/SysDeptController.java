package com.yiruantong.system.controller.permission;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import com.yiruantong.system.domain.permission.SysDept;
import com.yiruantong.system.domain.permission.bo.SysDeptBo;
import com.yiruantong.system.domain.permission.vo.SysDeptVo;
import com.yiruantong.system.mapper.permission.SysDeptMapper;
import com.yiruantong.system.service.permission.ISysDeptService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 部门信息
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/permission/dept")
public class SysDeptController extends AbstractController<SysDeptMapper, SysDept, SysDeptVo, SysDeptBo> {
  private final ISysDeptService deptService;

  /**
   * 获取部门列表
   */
  @SaCheckPermission("system:dept:list")
  @GetMapping("/list")
  public R<List<SysDeptVo>> list(SysDeptBo dept) {
    List<SysDeptVo> depts = deptService.selectDeptList(dept);
    return R.ok(depts);
  }

  /**
   * 查询部门列表（排除节点）
   *
   * @param deptId 部门ID
   */
  @SaCheckPermission("system:dept:list")
  @GetMapping("/list/exclude/{deptId}")
  public R<List<SysDeptVo>> excludeChild(@PathVariable(value = "deptId", required = false) Long deptId) {
    List<SysDeptVo> depts = deptService.selectDeptList(new SysDeptBo());
    depts.removeIf(d -> d.getDeptId().equals(deptId)
      || StringUtils.splitList(d.getAncestors()).contains(Convert.toStr(deptId)));
    return R.ok(depts);
  }

  /**
   * 根据部门编号获取详细信息
   *
   * @param deptId 部门ID
   */
  @SaCheckPermission("system:dept:query")
  @GetMapping(value = "/{deptId}")
  public R<SysDeptVo> getInfo(@PathVariable Long deptId) {
    deptService.checkDeptDataScope(deptId);
    return R.ok(deptService.selectDeptById(deptId));
  }

  /**
   * 新增部门
   */
  @SaCheckPermission("system:dept:add")
  @Log(title = "部门管理", businessType = BusinessType.INSERT)
  @PostMapping
  public R<Void> add(@Validated @RequestBody SysDeptBo dept) {
    if (!deptService.checkDeptNameUnique(dept)) {
      return R.fail("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
    }
    return toAjax(deptService.insertDept(dept));
  }

  /**
   * 修改部门
   */
  @SaCheckPermission("system:dept:edit")
  @Log(title = "部门管理", businessType = BusinessType.UPDATE)
  @PutMapping
  public R<Void> editData(@Validated @RequestBody SysDeptBo dept) {
    Long deptId = dept.getDeptId();
    deptService.checkDeptDataScope(deptId);
    if (!deptService.checkDeptNameUnique(dept)) {
      return R.fail("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
    } else if (dept.getParentId().equals(deptId)) {
      return R.fail("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
    } else if (UserConstants.DEPT_DISABLE == dept.getEnable()) {
      if (deptService.selectNormalChildrenDeptById(deptId) > 0) {
        return R.fail("该部门包含未停用的子部门!");
      } else if (deptService.checkDeptExistUser(deptId)) {
        return R.fail("该部门下存在已分配用户，不能禁用!");
      }
    }
    return toAjax(deptService.updateDept(dept));
  }

  /**
   * 删除部门
   *
   * @param deptId 部门ID
   */
  @SaCheckPermission("system:dept:remove")
  @Log(title = "部门管理", businessType = BusinessType.DELETE)
  @DeleteMapping("/{deptId}")
  public R<Void> remove(@PathVariable Long deptId) {
    if (deptService.hasChildByDeptId(deptId)) {
      return R.warn("存在下级部门,不允许删除");
    }
    if (deptService.checkDeptExistUser(deptId)) {
      return R.warn("部门存在用户,不允许删除");
    }
    deptService.checkDeptDataScope(deptId);
    return toAjax(deptService.deleteDeptById(deptId));
  }

  /**
   * 查询部门的父级部门
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getDeptInfo")
  public R<SysDept> getClientInfo(@RequestBody Map<String, Object> map) {
    Long deptId = Convert.toLong(map.get("deptId"));
    SysDept deptInfo = deptService.getDeptInfo(deptId);
    return R.ok(deptInfo);
  }
}

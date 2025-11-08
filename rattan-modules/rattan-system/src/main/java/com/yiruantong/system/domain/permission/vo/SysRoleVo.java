package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.system.domain.permission.SysRole;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息视图对象 sys_role
 *
 * @author YiRuanTong
 * @date 2024-07-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRole.class)
public class SysRoleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 角色ID
   */
  @ExcelProperty(value = "角色ID")
  private Long roleId;

  /**
   * 租户编号
   */
  @ExcelProperty(value = "租户编号")
  private String tenantId;

  /**
   * 角色名称
   */
  @ExcelProperty(value = "角色名称")
  private String roleName;

  /**
   * 角色权限字符串
   */
  @ExcelProperty(value = "角色权限字符串")
  private String roleKey;

  /**
   * 显示顺序
   */
  @ExcelProperty(value = "显示顺序")
  private Integer orderNum;

  /**
   * 数据范围
   */
  @ExcelProperty(value = "数据范围")
  private String dataScope;

  /**
   * 菜单树选择项是否关联显示
   */
  @ExcelProperty(value = "菜单树选择项是否关联显示")
  private Byte menuCheckStrictly;

  /**
   * 部门树选择项是否关联显示
   */
  @ExcelProperty(value = "部门树选择项是否关联显示")
  private Byte deptCheckStrictly;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 用户是否存在此角色标识 默认不存在
   */
  private boolean flag = false;

  public boolean isSuperAdmin() {
    return UserConstants.SUPER_ADMIN_ID.equals(this.roleId);
  }
}

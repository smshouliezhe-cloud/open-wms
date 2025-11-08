package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.permission.SysRoleAuth;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 功能权限视图对象 sys_role_auth
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleAuth.class)
public class SysRoleAuthVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 权限ID
   */
  @ExcelProperty(value = "权限ID")
  private Long authId;

  /**
   * 角色ID
   */
  @ExcelProperty(value = "角色ID")
  private Long roleId;

  /**
   * 用户ID
   */
  @ExcelProperty(value = "用户ID")
  private Long userId;

  /**
   * 栏目ID
   */
  @ExcelProperty(value = "栏目ID")
  private Long menuId;

  /**
   * 权限值
   */
  @ExcelProperty(value = "权限值")
  private String authValue;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 扩展字段
   */
  @ExcelProperty(value = "扩展字段")
  private Map<String, Object> expandFields;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 更新时间
   */
  @ExcelProperty(value = "更新时间")
  private Date updateTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;


}

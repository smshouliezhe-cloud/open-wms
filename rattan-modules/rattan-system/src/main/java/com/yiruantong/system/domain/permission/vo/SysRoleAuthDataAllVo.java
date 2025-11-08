package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.permission.SysRoleAuthDataAll;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 全部数据权限视图对象 sys_role_auth_data_all
 *
 * @author YiRuanTong
 * @date 2023-10-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleAuthDataAll.class)
public class SysRoleAuthDataAllVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 权限ID
   */
  @ExcelProperty(value = "权限ID")
  private Long authDataAllId;

  /**
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private Long moduleId;

  /**
   * 角色ID
   */
  @ExcelProperty(value = "角色ID")
  private Long roleId;

  /**
   * 全部权限
   */
  @ExcelProperty(value = "全部权限")
  private Long isAllAuth;

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

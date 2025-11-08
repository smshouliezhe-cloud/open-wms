package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.permission.SysRoleAuthData;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 数据权限视图对象 sys_role_auth_data
 *
 * @author YiRuanTong
 * @date 2024-01-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleAuthData.class)
public class SysRoleAuthDataVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 权限ID
   */
  @ExcelProperty(value = "权限ID")
  private Long authDataId;

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
   * 用户ID
   */
  @ExcelProperty(value = "用户ID")
  private Long userId;

  /**
   * 模块项ID
   */
  @ExcelProperty(value = "模块项ID")
  private Long nodeId;

  /**
   * 层次顺序
   */
  @ExcelProperty(value = "层次顺序")
  private Long levelId;

  /**
   * 权限值
   */
  @ExcelProperty(value = "权限值")
  private String authValue;

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

  /**
   * 模块项名称
   */
  @ExcelProperty(value = "模块项名称")
  private String nodeName;

  /**
   * 节点类别
   */
  @ExcelProperty(value = "节点类别")
  private String nodeType;


}

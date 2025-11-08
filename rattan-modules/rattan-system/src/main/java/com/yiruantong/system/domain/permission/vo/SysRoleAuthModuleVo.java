package com.yiruantong.system.domain.permission.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.permission.SysRoleAuthModule;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;


/**
 * 权限模块视图对象 sys_role_auth_module
 *
 * @author YiRuanTong
 * @date 2024-01-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysRoleAuthModule.class)
public class SysRoleAuthModuleVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long moduleId;

  /**
   * 模块名称
   */
  @ExcelProperty(value = "模块名称")
  private String moduleName;

  /**
   * 模块SQL
   */
  @ExcelProperty(value = "模块SQL")
  private String sqlScript;

  /**
   * 是否启用
   */
  @ExcelProperty(value = "是否启用")
  private Long enable;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 编辑类型
   */
  @ExcelProperty(value = "编辑类型")
  private String editType;

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
   * module_type
   */
  @ExcelProperty(value = "module_type")
  private String moduleType;


}

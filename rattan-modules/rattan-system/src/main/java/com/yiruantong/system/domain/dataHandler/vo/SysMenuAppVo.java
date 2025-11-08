package com.yiruantong.system.domain.dataHandler.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 【请填写功能名称】视图对象 sys_menu_app
 *
 * @author ${author}
 * @date 2024-09-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysMenuApp.class)
public class SysMenuAppVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private Long menuId;

  /**
   * 父级ID
   */
  @ExcelProperty(value = "父级ID")
  private Long parentId;

  /**
   * 站点ID
   */
  @ExcelProperty(value = "站点ID")
  private Long siteId;

  /**
   * 模块名称
   */
  @ExcelProperty(value = "模块名称")
  private String menuName;

  /**
   * 模块英文名称
   */
  @ExcelProperty(value = "模块英文名称")
  private String menuEnName;

  /**
   * vue_auth
   */
  @ExcelProperty(value = "vue_auth")
  private String vueAuth;

  /**
   * vue_url
   */
  @ExcelProperty(value = "vue_url")
  private String vueUrl;

  /**
   * vue_file_path
   */
  @ExcelProperty(value = "vue_file_path")
  private String vueFilePath;

  /**
   * vue_name
   */
  @ExcelProperty(value = "vue_name")
  private String vueName;

  /**
   * 是否展开
   */
  @ExcelProperty(value = "是否展开")
  private Byte isOpen;

  /**
   * 系统类别
   */
  @ExcelProperty(value = "系统类别")
  private Long typeId;

  /**
   * 描述
   */
  @ExcelProperty(value = "描述")
  private String description;

  /**
   * 级别
   */
  @ExcelProperty(value = "级别")
  private Long levelId;

  /**
   * 图标
   */
  @ExcelProperty(value = "图标")
  private String icon;

  /**
   * 排序号
   */
  @ExcelProperty(value = "排序号")
  private Long orderNum;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Byte enable;

  /**
   * 帮助地址1
   */
  @ExcelProperty(value = "帮助地址1")
  private String helpUrl1;

  /**
   * 帮助地址2
   */
  @ExcelProperty(value = "帮助地址2")
  private String helpUrl2;

  /**
   * 帮助地址3
   */
  @ExcelProperty(value = "帮助地址3")
  private String helpUrl3;

  /**
   * 数据
   */
  @ExcelProperty(value = "数据")
  private String vueData;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
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
   * 图标颜色
   */
  @ExcelProperty(value = "图标颜色")
  private String iconColor;

  /**
   * 模块类型
   */
  @ExcelProperty(value = "模块类型")
  private String menuType;

  /**
   * 置顶权重
   */
  @ExcelProperty(value = "置顶权重")
  private Long topNum;

  /**
   * 菜单编号
   */
  @ExcelProperty(value = "菜单编号")
  private String menuCode;


}

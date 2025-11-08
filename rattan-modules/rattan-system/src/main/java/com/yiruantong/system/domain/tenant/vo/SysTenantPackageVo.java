package com.yiruantong.system.domain.tenant.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.system.domain.tenant.SysTenantPackage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 租户套餐视图对象 sys_tenant_package
 *
 * @author YiRuanTong
 * @date 2024-05-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenantPackage.class)
public class SysTenantPackageVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 租户套餐id
   */
  @ExcelProperty(value = "租户套餐id")
  private Long packageId;

  /**
   * 套餐名称
   */
  @ExcelProperty(value = "套餐名称")
  private String packageName;

  /**
   * 关联菜单id
   */
  @ExcelProperty(value = "关联菜单id")
  private String menuIds;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 关联显示
   */
  @ExcelProperty(value = "关联显示")
  private Byte menuCheckStrictly;

  /**
   * 套餐状态
   */
  @ExcelProperty(value = "套餐状态")
  private Byte status;

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
   * 登录模板
   */
  @ExcelProperty(value = "登录模板")
  private String loginTemplate;

  /**
   * 主界面模板
   */
  @ExcelProperty(value = "主界面模板")
  private String mainTemplate;

  /**
   * 系统全称
   */
  @ExcelProperty(value = "系统全称")
  private String sysFullName;

  /**
   * 系统简称
   */
  @ExcelProperty(value = "系统简称")
  private String sysShortName;

  /**
   * 短logo
   */
  @ExcelProperty(value = "短logo")
  private String logoShort;

  /**
   * 长logo
   */
  @ExcelProperty(value = "长logo")
  private String logoLong;

  /**
   * 套餐切图
   */
  @ExcelProperty(value = "套餐切图")
  private String packageImage;

  /**
   * 套餐标签
   */
  @ExcelProperty(value = "套餐标签")
  private String tags;

  /**
   * 热门
   */
  @ExcelProperty(value = "热门")
  private Byte hot;

  /**
   * 官方
   */
  @ExcelProperty(value = "官方")
  private Byte official;

  /**
   * 应用次数
   */
  @ExcelProperty(value = "应用次数")
  private Long frequency;

  /**
   * 套餐类别
   */
  @ExcelProperty(value = "套餐类别")
  private String packageType;


}

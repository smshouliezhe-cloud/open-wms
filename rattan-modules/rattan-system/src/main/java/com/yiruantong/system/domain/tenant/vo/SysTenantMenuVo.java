package com.yiruantong.system.domain.tenant.vo;

import com.yiruantong.system.domain.tenant.SysTenantMenu;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yiruantong.common.excel.annotation.ExcelDictFormat;
import com.yiruantong.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.util.List;
import java.util.Map;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 租户套餐菜单视图对象 sys_tenant_menu
 *
 * @author YRT
 * @date 2025-04-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysTenantMenu.class)
public class SysTenantMenuVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

      /**
       * 账套菜单ID
       */
      @ExcelProperty(value = "账套菜单ID")
    private Long tentantMenuId;

      /**
       * 菜单ID
       */
      @ExcelProperty(value = "菜单ID")
    private Long menuId;

      /**
       * 菜单名称
       */
      @ExcelProperty(value = "菜单名称")
    private String menuName;

      /**
       * 父菜单ID
       */
      @ExcelProperty(value = "父菜单ID")
    private Long parentId;

      /**
       * 显示顺序
       */
      @ExcelProperty(value = "显示顺序")
    private Long orderNum;

      /**
       * 路由地址
       */
      @ExcelProperty(value = "路由地址")
    private String path;

      /**
       * 组件路径
       */
      @ExcelProperty(value = "组件路径")
    private String component;

      /**
       * 组件名称
       */
      @ExcelProperty(value = "组件名称")
    private String componentName;

      /**
       * 路由参数
       */
      @ExcelProperty(value = "路由参数")
    private String queryParam;

      /**
       * 是否为外链（1是 0否）
       */
      @ExcelProperty(value = "是否为外链", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "1=是,0=否")
    private Byte isFrame;

      /**
       * 是否缓存（1缓存 0不缓存）
       */
      @ExcelProperty(value = "是否缓存", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "1=缓存,0=不缓存")
    private Byte isCache;

      /**
       * 菜单类型（M目录 C菜单 F按钮）
       */
      @ExcelProperty(value = "菜单类型", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "M=目录,C=菜单,F=按钮")
    private String menuType;

      /**
       * 显示状态（1显示 0隐藏）
       */
      @ExcelProperty(value = "显示状态", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "1=显示,0=隐藏")
    private Byte visible;

      /**
       * 菜单状态（1正常 0停用）
       */
      @ExcelProperty(value = "菜单状态", converter = ExcelDictConvert.class)
      @ExcelDictFormat(readConverterExp = "1=正常,0=停用")
    private Byte enable;

      /**
       * 权限标识
       */
      @ExcelProperty(value = "权限标识")
    private String perms;

      /**
       * 菜单图标
       */
      @ExcelProperty(value = "菜单图标")
    private String icon;

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
       * 焦点菜单
       */
      @ExcelProperty(value = "焦点菜单")
    private String activeMenu;

      /**
       * 外部url
       */
      @ExcelProperty(value = "外部url")
    private String isLink;

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
       * 动态路由
       */
      @ExcelProperty(value = "动态路由")
    private String dynamicPath;

      /**
       * 展开菜单宽度
       */
      @ExcelProperty(value = "展开菜单宽度")
    private String subMenuWidth;

      /**
       * 扩展字段
       */
      @ExcelProperty(value = "扩展字段")
    private Map<String, Object> expandFields;

  
}

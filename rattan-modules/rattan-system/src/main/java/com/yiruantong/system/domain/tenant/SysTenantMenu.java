package com.yiruantong.system.domain.tenant;

  import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  

import java.io.Serial;

/**
 * 租户套餐菜单对象 sys_tenant_menu
 *
 * @author YRT
 * @date 2025-04-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_tenant_menu", autoResultMap = true)
public class SysTenantMenu extends BaseEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 账套菜单ID
   */
    @TableId(value = "tentant_menu_id")
  private Long tentantMenuId;

  /**
   * 菜单ID
   */
  private Long menuId;

  /**
   * 菜单名称
   */
  private String menuName;

  /**
   * 父菜单ID
   */
  private Long parentId;

  /**
   * 显示顺序
   */
  private Long orderNum;

  /**
   * 路由地址
   */
  private String path;

  /**
   * 组件路径
   */
  private String component;

  /**
   * 组件名称
   */
  private String componentName;

  /**
   * 路由参数
   */
  private String queryParam;

  /**
   * 是否为外链（1是 0否）
   */
  private Byte isFrame;

  /**
   * 是否缓存（1缓存 0不缓存）
   */
  private Byte isCache;

  /**
   * 菜单类型（M目录 C菜单 F按钮）
   */
  private String menuType;

  /**
   * 显示状态（1显示 0隐藏）
   */
  private Byte visible;

  /**
   * 菜单状态（1正常 0停用）
   */
  private Byte enable;

  /**
   * 权限标识
   */
  private String perms;

  /**
   * 菜单图标
   */
  private String icon;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 焦点菜单
   */
  private String activeMenu;

  /**
   * 外部url
   */
  private String isLink;

  /**
   * 租户套餐id
   */
  private Long packageId;

  /**
   * 套餐名称
   */
  private String packageName;

  /**
   * 动态路由
   */
  private String dynamicPath;

  /**
   * 展开菜单宽度
   */
  private String subMenuWidth;

  /**
   * 扩展字段
   */
    @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;


}

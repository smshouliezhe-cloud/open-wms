package com.yiruantong.system.domain.tenant.bo;

import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.system.domain.tenant.SysTenantMenu;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;


/**
 * 租户套餐菜单业务对象 sys_tenant_menu
 *
 * @author YRT
 * @date 2025-04-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysTenantMenu.class, reverseConvertGenerate = false)
public class SysTenantMenuBo extends BaseEntity {

  /**
   * 账套菜单ID
   */
  @NotNull(message = "账套菜单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long tentantMenuId;

  /**
   * 菜单ID
   */
  @NotNull(message = "菜单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 菜单名称
   */
  @NotBlank(message = "菜单名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuName;

  /**
   * 父菜单ID
   */
  @NotNull(message = "父菜单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 显示顺序
   */
  @NotNull(message = "显示顺序不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 路由地址
   */
  @NotBlank(message = "路由地址不能为空", groups = {AddGroup.class, EditGroup.class})
  private String path;

  /**
   * 组件路径
   */
  @NotBlank(message = "组件路径不能为空", groups = {AddGroup.class, EditGroup.class})
  private String component;

  /**
   * 组件名称
   */
  @NotBlank(message = "组件名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String componentName;

  /**
   * 路由参数
   */
  @NotBlank(message = "路由参数不能为空", groups = {AddGroup.class, EditGroup.class})
  private String queryParam;

  /**
   * 是否为外链（1是 0否）
   */
  @NotNull(message = "是否为外链（1是 0否）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isFrame;

  /**
   * 是否缓存（1缓存 0不缓存）
   */
  @NotNull(message = "是否缓存（1缓存 0不缓存）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isCache;

  /**
   * 菜单类型（M目录 C菜单 F按钮）
   */
  @NotBlank(message = "菜单类型（M目录 C菜单 F按钮）不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuType;

  /**
   * 显示状态（1显示 0隐藏）
   */
  @NotNull(message = "显示状态（1显示 0隐藏）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte visible;

  /**
   * 菜单状态（1正常 0停用）
   */
  @NotNull(message = "菜单状态（1正常 0停用）不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 权限标识
   */
  @NotBlank(message = "权限标识不能为空", groups = {AddGroup.class, EditGroup.class})
  private String perms;

  /**
   * 菜单图标
   */
  @NotBlank(message = "菜单图标不能为空", groups = {AddGroup.class, EditGroup.class})
  private String icon;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除人id
   */
  @NotNull(message = "删除人id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;

  /**
   * 焦点菜单
   */
  @NotBlank(message = "焦点菜单不能为空", groups = {AddGroup.class, EditGroup.class})
  private String activeMenu;

  /**
   * 外部url
   */
  @NotBlank(message = "外部url不能为空", groups = {AddGroup.class, EditGroup.class})
  private String isLink;

  /**
   * 租户套餐id
   */
  @NotNull(message = "租户套餐id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long packageId;

  /**
   * 套餐名称
   */
  @NotBlank(message = "套餐名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String packageName;

  /**
   * 动态路由
   */
  @NotBlank(message = "动态路由不能为空", groups = {AddGroup.class, EditGroup.class})
  private String dynamicPath;

  /**
   * 展开菜单宽度
   */
  @NotBlank(message = "展开菜单宽度不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subMenuWidth;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;


}

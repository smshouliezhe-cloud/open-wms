package com.yiruantong.system.domain.core.bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.system.domain.core.SysMenu;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 菜单权限业务对象 sys_menu
 *
 * @author YiRuanTong
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysMenu.class, reverseConvertGenerate = false)
public class SysMenuBo extends BaseEntity {
  public final int MAX = 50;
  /**
   * 菜单ID
   */
  @NotNull(message = "菜单ID不能为空", groups = {EditGroup.class})
  private Long menuId;

  /**
   * 父菜单ID
   */
  private Long parentId;

  /**
   * 菜单名称
   */
  @NotBlank(message = "菜单名称不能为空", groups = {AddGroup.class, EditGroup.class})
  @Size(min = 0, max = MAX, message = "菜单名称长度不能超过{max}个字符")
  private String menuName;

  /**
   * 显示顺序
   */
  @NotNull(message = "显示顺序不能为空", groups = {AddGroup.class, EditGroup.class})
  private Integer orderNum;

  /**
   * 路由地址
   */
  @Size(min = 0, max = 200, message = "路由地址不能超过{max}个字符")
  private String path;

  /**
   * 组件路径
   */
  @Size(min = 0, max = 200, message = "组件路径不能超过{max}个字符")
  private String component;

  /**
   * 组件路径
   */
  @Size(min = 0, max = 200, message = "组件路径不能超过{max}个字符")
  private String componentName;

  /**
   * 路由参数
   */
  private String queryParam;

  /**
   * 是否为外链（0是 1否）
   */
  private Byte isFrame;

  /**
   * 是否缓存（0缓存 1不缓存）
   */
  private Byte isCache;

  /**
   * 菜单类型（M目录 C菜单 F按钮）
   */
  @NotBlank(message = "菜单类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuType;

  /**
   * 显示状态（0显示 1隐藏）
   */
  private Byte visible;

  /**
   * 菜单状态（0正常 1停用）
   */
  private Byte enable;

  /**
   * 权限标识
   */
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Size(min = 0, max = 100, message = "权限标识长度不能超过{max}个字符")
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
   * 焦点菜单
   */
  private String activeMenu;

  /**
   * 外部url
   */
  private String isLink;

  /**
   * 动态路由
   */
  private String dynamicPath;

  /**
   * 动态路由
   */
  private BigDecimal subMenuWidth;

  /**
   * 扩展字段
   */
  private Map<String, Object> expandFields;
}

package com.yiruantong.system.domain.core;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.yiruantong.common.core.constant.Constants;
import com.yiruantong.common.core.constant.UserConstants;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 菜单权限表 sys_menu
 *
 * @author YiRuanTong
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu", autoResultMap = true)
public class SysMenu extends BaseEntity {

  /**
   * 菜单ID
   */
  @TableId(value = "menu_id", type = IdType.AUTO)
  private Long menuId;

  /**
   * 父菜单ID
   */
  private Long parentId;

  /**
   * 菜单名称
   */
  private String menuName;

  /**
   * 显示顺序
   */
  private Integer orderNum;

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
   * 类型（M目录 C菜单 F按钮）
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
   * 权限字符串
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
   * 父菜单名称
   */
  @TableField(exist = false)
  private String parentName;

  /**
   * 创建部门
   */
  private Long createDept;

  /**
   * 焦点菜单
   */
  private String activeMenu;

  /**
   * 外部url
   */
  private String isLink;

  /**
   * 子菜单
   */
  @TableField(exist = false)
  private List<SysMenu> children = new ArrayList<>();

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
  @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 内链域名特殊字符替换
   */
  public static String innerLinkReplaceEach(String path) {
    return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."}, new String[]{"", "", "", "/"});
  }

  /**
   * 获取路由名称
   */
  public String getRouteName() {
    String routerName = StringUtils.capitalize(path);
    // 非外链并且是一级目录（类型为目录）
    if (isMenuFrame()) {
      routerName = StringUtils.EMPTY;
    }
    return routerName;
  }

  /**
   * 获取路由地址
   */
  public String getRouterPath() {
    String routerPath = this.path;
    // 内链打开外网方式
    if (getParentId() != 0L && isInnerLink()) {
      routerPath = innerLinkReplaceEach(routerPath);
    }
    // 非外链并且是一级目录（类型为目录）
    if (0L == getParentId() && UserConstants.TYPE_DIR.equals(getMenuType()) && UserConstants.NO_FRAME == getIsFrame()) {
      routerPath = this.path;
    }
    // 非外链并且是一级目录（类型为菜单）
    else if (isMenuFrame()) {
      routerPath = "/";
    }
    return routerPath;
  }

  /**
   * 获取组件信息
   */
  public String getComponentInfo() {
    String component = UserConstants.LAYOUT;
    if (StringUtils.isNotEmpty(this.component) && !isMenuFrame()) {
      component = this.component;
    } else if (StringUtils.isEmpty(this.component) && getParentId() != 0L && isInnerLink()) {
      component = UserConstants.INNER_LINK;
    } else if (StringUtils.isEmpty(this.component) && isParentView()) {
      component = UserConstants.PARENT_VIEW;
    }
    return component;
  }

  /**
   * iframe菜单
   */
  public boolean isMenuFrame() {
    return getParentId() == 0L && UserConstants.TYPE_MENU.equals(menuType) && isFrame == UserConstants.YES_FRAME;
  }

  /**
   * 是否为内链组件
   */
  public boolean isInnerLink() {
    return isFrame == UserConstants.YES_FRAME && StringUtils.ishttp(path);
  }

  /**
   * 是否为parent_view组件
   */
  public boolean isParentView() {
    return getParentId() != 0L && UserConstants.TYPE_DIR.equals(menuType);
  }
}

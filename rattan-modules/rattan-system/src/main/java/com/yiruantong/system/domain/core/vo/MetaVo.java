package com.yiruantong.system.domain.core.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yiruantong.common.core.utils.StringUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 路由显示信息
 *
 * @author rattan
 */
@Data
public class MetaVo {

  /**
   * 模块ID
   */
  private Long menuId;

  /**
   * 设置该路由在侧边栏和面包屑中展示的名字
   */
  private String title;

  /**
   * 内链地址（http(s)://开头）
   */
  private String isLink;

  /**
   * 焦点菜单
   */
  private String activeMenu;

  /**
   * 是否隐藏
   */
  @JsonProperty("isHide")
  private boolean isHide;

  /**
   * 设置为true，则不会被 <keep-alive>缓存
   */
  @JsonProperty("isKeepAlive")
  private boolean isKeepAlive;

  /**
   * 是否固定在tagView
   */
  @JsonProperty("isAffix")
  private boolean isAffix;

  /**
   * 是否frame加载
   */
  @JsonProperty("isIframe")
  private boolean isIframe;

  /**
   * 设置该路由的图标，对应路径src/assets/icons/svg
   */
  private String icon;

  /**
   * 动态路由地址
   */
  private String dynamicPath;

  /**
   * 路由参数
   */
  private Map<String, Object> routeParams;

  /**
   * 动态路由
   */
  private BigDecimal subMenuWidth;

  public MetaVo(Long menuId, String title, String icon, Map<String, Object> routeParams) {
    this.menuId = menuId;
    this.title = title;
    this.icon = icon;
    this.routeParams = routeParams;
  }

  public MetaVo(Long menuId, String title, String icon, boolean isIframe) {
    this.menuId = menuId;
    this.title = title;
    this.icon = icon;
    this.isIframe = isIframe;
  }

  public MetaVo(Long menuId, String title, String icon, String isLink) {
    this.menuId = menuId;
    this.title = title;
    this.icon = icon;
    this.isLink = isLink;
  }

  public MetaVo(Long menuId, String title, String icon, String isLink, Map<String, Object> routeParams) {
    this.menuId = menuId;
    this.title = title;
    this.icon = icon;
    this.isLink = isLink;
    this.routeParams = routeParams;
  }

  public MetaVo(Long menuId, String title, boolean isHide, boolean isKeepAlive, String icon, String activeMenu, String isLink, boolean isIframe, String dynamicPath, BigDecimal subMenuWidth, Map<String, Object> routeParams) {
    this.menuId = menuId;
    this.title = title;
    this.isLink = "";
    this.isHide = isHide;
    this.isKeepAlive = isKeepAlive;
    this.isAffix = false;
    this.isIframe = isIframe;
    this.activeMenu = activeMenu;
    this.dynamicPath = dynamicPath;
    if (StringUtils.ishttp(isLink)) {
      this.isLink = isLink;
    }
    this.icon = icon;
    this.subMenuWidth = subMenuWidth;
    this.routeParams = routeParams;
  }

  public MetaVo(Long menuId, String title, boolean isHide, boolean isKeepAlive, String icon, boolean isAffix, String isLink) {
    this.menuId = menuId;
    this.title = title;
    this.isHide = isHide;
    this.isKeepAlive = isKeepAlive;
    this.isAffix = isAffix;
    this.isIframe = isIframe;
    if (StringUtils.ishttp(isLink)) {
      this.isLink = isLink;
    }
    this.icon = icon;
    this.isLink = isLink;
  }

  public MetaVo(Long menuId, String title, boolean isHide, boolean isKeepAlive, String icon, boolean isAffix, String isLink, String activeMenu) {
    this.menuId = menuId;
    this.isHide = isHide;
    this.isKeepAlive = isKeepAlive;
    this.isAffix = isAffix;
    this.isIframe = isIframe;
    this.activeMenu = activeMenu;
    if (StringUtils.ishttp(isLink)) {
      this.isLink = isLink;
    }
    this.icon = icon;
    this.isLink = isLink;
  }
}

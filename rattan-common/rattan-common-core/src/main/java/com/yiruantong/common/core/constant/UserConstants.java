package com.yiruantong.common.core.constant;

/**
 * 用户常量信息
 *
 * @author rattan
 */
public interface UserConstants {

  /**
   * 平台内系统用户的唯一标志
   */
  String SYS_USER = "SYS_USER";

  /**
   * 正常状态
   */
  String NORMAL = "0";

  /**
   * 异常状态
   */
  String EXCEPTION = "1";

  /**
   * 用户正常状态
   */
  byte USER_NORMAL = 0;

  /**
   * 用户封禁状态
   */
  byte USER_DISABLE = 1;

  /**
   * 角色正常状态
   */
  byte ROLE_NORMAL = 0;

  /**
   * 角色封禁状态
   */
  byte ROLE_DISABLE = 1;

  /**
   * 部门正常状态
   */
  byte DEPT_NORMAL = 1;

  /**
   * 部门停用状态
   */
  byte DEPT_DISABLE = 0;

  /**
   * 岗位正常状态
   */
  byte POST_NORMAL = 1;

  /**
   * 岗位停用状态
   */
  byte POST_DISABLE = 0;

  /**
   * 字典正常状态
   */
  String DICT_NORMAL = "0";

  /**
   * 是否为系统默认（是）
   */
  String YES = "Y";

  /**
   * 是否菜单外链（是）
   */
  byte YES_FRAME = 1;

  /**
   * 是否菜单外链（否）
   */
  byte NO_FRAME = 0;

  /**
   * 菜单正常状态
   */
  byte MENU_NORMAL = 1;

  /**
   * 菜单显示
   */
  byte MENU_VISIBLE = 1;

  /**
   * 菜单停用状态
   */
  String MENU_DISABLE = "1";

  /**
   * 菜单类型（目录）
   */
  String TYPE_DIR = "M";

  /**
   * 菜单类型（菜单）
   */
  String TYPE_MENU = "C";

  /**
   * 菜单类型（按钮）
   */
  String TYPE_BUTTON = "F";

  /**
   * Layout组件标识
   */
  String LAYOUT = "Layout";

  /**
   * ParentView组件标识
   */
  String PARENT_VIEW = "ParentView";

  /**
   * InnerLink组件标识
   */
  String INNER_LINK = "InnerLink";

  /**
   * 用户名长度限制
   */
  int USERNAME_MIN_LENGTH = 2;
  int USERNAME_MAX_LENGTH = 20;

  /**
   * 密码长度限制
   */
  int PASSWORD_MIN_LENGTH = 5;
  int PASSWORD_MAX_LENGTH = 20;

  /**
   * 超级管理员ID
   */
  Long SUPER_ADMIN_ID = 1L;

}

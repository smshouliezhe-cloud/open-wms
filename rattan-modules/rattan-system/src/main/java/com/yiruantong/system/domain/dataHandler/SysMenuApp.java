package com.yiruantong.system.domain.dataHandler;

import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serial;

/**
 * 【请填写功能名称】对象 sys_menu_app
 *
 * @author ${author}
 * @date 2024-09-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_menu_app", autoResultMap = true)
public class SysMenuApp extends BaseEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 模块ID
   */
  @TableId(value = "menu_id")
  private Long menuId;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 站点ID
   */
  private Long siteId;

  /**
   * 模块名称
   */
  private String menuName;

  /**
   * 模块英文名称
   */
  private String menuEnName;

  /**
   * vue_auth
   */
  private String vueAuth;

  /**
   * vue_url
   */
  private String vueUrl;

  /**
   * vue_file_path
   */
  private String vueFilePath;

  /**
   * vue_name
   */
  private String vueName;

  /**
   * 是否展开
   */
  private Byte isOpen;

  /**
   * 系统类别
   */
  private Long typeId;

  /**
   * 描述
   */
  private String description;

  /**
   * 级别
   */
  private Long levelId;

  /**
   * 图标
   */
  private String icon;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 是否可用
   */
  private Byte enable;

  /**
   * 帮助地址1
   */
  private String helpUrl1;

  /**
   * 帮助地址2
   */
  private String helpUrl2;

  /**
   * 帮助地址3
   */
  private String helpUrl3;

  /**
   * 数据
   */
  private String vueData;

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
   * 图标颜色
   */
  private String iconColor;

  /**
   * 模块类型
   */
  private String menuType;

  /**
   * 置顶权重
   */
  private Long topNum;

  /**
   * 菜单编号
   */
  private String menuCode;


}

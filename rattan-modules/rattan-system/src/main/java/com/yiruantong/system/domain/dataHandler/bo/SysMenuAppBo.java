package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 【请填写功能名称】业务对象 sys_menu_app
 *
 * @author ${author}
 * @date 2024-09-07
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysMenuApp.class, reverseConvertGenerate = false)
public class SysMenuAppBo extends BaseEntity {

  /**
   * 模块ID
   */
  @NotNull(message = "模块ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 站点ID
   */
  @NotNull(message = "站点ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long siteId;

  /**
   * 模块名称
   */
  @NotBlank(message = "模块名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuName;

  /**
   * 模块英文名称
   */
  @NotBlank(message = "模块英文名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuEnName;

  /**
   * vue_auth
   */
  @NotBlank(message = "vue_auth不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueAuth;

  /**
   * vue_url
   */
  @NotBlank(message = "vue_url不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueUrl;

  /**
   * vue_file_path
   */
  @NotBlank(message = "vue_file_path不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueFilePath;

  /**
   * vue_name
   */
  @NotBlank(message = "vue_name不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueName;

  /**
   * 是否展开
   */
  @NotNull(message = "是否展开不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte isOpen;

  /**
   * 系统类别
   */
  @NotNull(message = "系统类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long typeId;

  /**
   * 描述
   */
  @NotBlank(message = "描述不能为空", groups = {AddGroup.class, EditGroup.class})
  private String description;

  /**
   * 级别
   */
  @NotNull(message = "级别不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long levelId;

  /**
   * 图标
   */
  @NotBlank(message = "图标不能为空", groups = {AddGroup.class, EditGroup.class})
  private String icon;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Byte enable;

  /**
   * 帮助地址1
   */
  @NotBlank(message = "帮助地址1不能为空", groups = {AddGroup.class, EditGroup.class})
  private String helpUrl1;

  /**
   * 帮助地址2
   */
  @NotBlank(message = "帮助地址2不能为空", groups = {AddGroup.class, EditGroup.class})
  private String helpUrl2;

  /**
   * 帮助地址3
   */
  @NotBlank(message = "帮助地址3不能为空", groups = {AddGroup.class, EditGroup.class})
  private String helpUrl3;

  /**
   * 数据
   */
  @NotBlank(message = "数据不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueData;

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
   * 图标颜色
   */
  @NotBlank(message = "图标颜色不能为空", groups = {AddGroup.class, EditGroup.class})
  private String iconColor;

  /**
   * 模块类型
   */
  @NotBlank(message = "模块类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuType;

  /**
   * 置顶权重
   */
  @NotNull(message = "置顶权重不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long topNum;

  /**
   * 菜单编号
   */
  @NotBlank(message = "菜单编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuCode;


}

package com.yiruantong.system.domain.magic.bo;

import com.yiruantong.system.domain.magic.MagicPage;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;
import java.util.Date;


/**
 * 页面开发业务对象 magic_page
 *
 * @author YRT
 * @date 2024-12-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MagicPage.class, reverseConvertGenerate = false)
public class MagicPageBo extends BaseEntity {

  /**
   * 主键
   */
  @NotNull(message = "主键不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long pageId;

  /**
   * 页面标题
   */
  @NotBlank(message = "页面标题不能为空", groups = {AddGroup.class, EditGroup.class})
  private String title;

  /**
   * 副标题
   */
  @NotBlank(message = "副标题不能为空", groups = {AddGroup.class, EditGroup.class})
  private String subtitle;

  /**
   * 父页面
   */
  @NotNull(message = "父页面不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 页面json
   */
  @NotBlank(message = "页面json不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pageschema;

  /**
   * 状态：0、正常，1、禁用
   */
  @NotNull(message = "状态：0、正常，1、禁用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long status;

  /**
   * 分类编码
   */
  @NotBlank(message = "分类编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String classify;

  /**
   * 文件描述
   */
  @NotBlank(message = "文件描述不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remarks;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotNull(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除时间
   */
  @NotNull(message = "删除时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date deleteTime;

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
   * 页面脚本
   */
  @NotBlank(message = "页面脚本不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pageScript;

  /**
   * 版本号
   */
  @NotNull(message = "版本号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long version;

  /**
   * 菜单ID
   */
  @NotNull(message = "菜单ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 表ID
   */
  @NotNull(message = "表ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long tableId;

  /**
   * 页面编号
   */
  @NotBlank(message = "页面编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pageCode;


}

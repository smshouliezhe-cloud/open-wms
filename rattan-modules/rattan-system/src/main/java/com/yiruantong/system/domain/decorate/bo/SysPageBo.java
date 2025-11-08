package com.yiruantong.system.domain.decorate.bo;

import com.yiruantong.system.domain.decorate.SysPage;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 页面装修业务对象 sys_page
 *
 * @author YRT
 * @date 2024-09-01
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysPage.class, reverseConvertGenerate = false)
public class SysPageBo extends BaseEntity {

  /**
   * 页面装修ID
   */
  @NotNull(message = "页面装修ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long pageId;

  /**
   * 页面名称
   */
  @NotBlank(message = "页面名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pageName;

  /**
   * 页面类型
   */
  @NotBlank(message = "页面类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pageType;

  /**
   * 页面编码
   */
  @NotBlank(message = "页面编码不能为空", groups = {AddGroup.class, EditGroup.class})
  private String pageCode;

  /**
   * jsonData
   */
  @NotBlank(message = "jsonData不能为空", groups = {AddGroup.class, EditGroup.class})
  private String jsonData;

  /**
   * 页面状态
   */
  @NotNull(message = "页面状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private Integer enable;

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
   * 父级id
   */
  @NotNull(message = "父级id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 完整父级ID
   */
  @NotBlank(message = "完整父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullId;

  /**
   * 父级名称
   */
  @NotBlank(message = "父级名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fullName;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;


}

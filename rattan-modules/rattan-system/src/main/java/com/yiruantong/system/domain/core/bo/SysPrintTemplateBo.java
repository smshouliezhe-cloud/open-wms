package com.yiruantong.system.domain.core.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.system.domain.core.SysPrintTemplate;

import java.util.Map;


/**
 * 系统打印模板业务对象 sys_print_template
 *
 * @author YRT
 * @date 2023-11-14
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysPrintTemplate.class, reverseConvertGenerate = false)
public class SysPrintTemplateBo extends BaseEntity {

  /**
   * 打印模板ID
   */
  @NotNull(message = "打印模板ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long printTemplateId;

  /**
   * 模板名称
   */
  @NotBlank(message = "模板名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String templateName;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 模块ID
   */
  @NotNull(message = "模块ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * JSON数据
   */
  @NotBlank(message = "JSON数据不能为空", groups = {AddGroup.class, EditGroup.class})
  private String vueData;

  /**
   * 模板类型
   */
  @NotBlank(message = "模板类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String templateType;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;


}

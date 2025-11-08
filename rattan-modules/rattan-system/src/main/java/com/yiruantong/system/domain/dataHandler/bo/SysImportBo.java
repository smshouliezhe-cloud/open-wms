package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysImport;
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
 * 导入设置业务对象 sys_import
 *
 * @author YRT
 * @date 2024-07-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysImport.class, reverseConvertGenerate = false)
public class SysImportBo extends BaseEntity {

  /**
   * 导入ID
   */
  @NotNull(message = "导入ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long importId;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 导入名称
   */
  @NotBlank(message = "导入名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String importName;

  /**
   * 导入类别
   */
  @NotNull(message = "导入类别不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long importType;

  /**
   * 模板路径
   */
  @NotBlank(message = "模板路径不能为空", groups = {AddGroup.class, EditGroup.class})
  private String templatePath;

  /**
   * 导入后执行sql
   */
  @NotBlank(message = "导入后执行sql不能为空", groups = {AddGroup.class, EditGroup.class})
  private String execSql;

  /**
   * 排序号
   */
  @NotNull(message = "排序号不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNum;

  /**
   * 扩展字段
   */
  @NotBlank(message = "扩展字段不能为空", groups = {AddGroup.class, EditGroup.class})
  private Map<String, Object> expandFields;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

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
   * 自定义导入ID
   */
  @NotNull(message = "自定义导入ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long customImportId;


}

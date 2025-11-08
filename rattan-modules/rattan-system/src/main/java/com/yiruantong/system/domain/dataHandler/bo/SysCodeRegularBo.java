package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysCodeRegular;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Map;


/**
 * 单据编码规则业务对象 sys_code_regular
 *
 * @author YRT
 * @date 2023-12-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysCodeRegular.class, reverseConvertGenerate = false)
public class SysCodeRegularBo extends BaseEntity {

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long regularId;

  /**
   * 父级ID
   */
  @NotNull(message = "父级ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long parentId;

  /**
   * 模块类别ID
   */
  @NotNull(message = "模块类别ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 模块ID
   */
  @NotBlank(message = "模块ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuName;

  /**
   * 模块名称
   */
  @NotBlank(message = "模块名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String code;

  /**
   * SQL语句
   */
  @NotBlank(message = "SQL语句不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regularSql;

  /**
   * 是否启用
   */
  @NotNull(message = "是否启用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

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
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;


}

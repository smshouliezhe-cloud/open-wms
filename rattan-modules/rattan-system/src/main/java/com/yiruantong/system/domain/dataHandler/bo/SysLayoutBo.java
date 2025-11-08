package com.yiruantong.system.domain.dataHandler.bo;

import com.yiruantong.system.domain.dataHandler.SysLayout;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;


/**
 * 【请填写功能名称】业务对象 sys_layout
 *
 * @author ${author}
 * @date 2024-01-18
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = SysLayout.class, reverseConvertGenerate = false)
public class SysLayoutBo extends BaseEntity {

  /**
   * 首页Id
   */
  @NotNull(message = "首页Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long layoutId;

  /**
   * jsonData
   */
  @NotBlank(message = "jsonData不能为空", groups = {AddGroup.class, EditGroup.class})
  private String jsonData;

  /**
   * 备注
   */
  @NotBlank(message = "备注不能为空", groups = {AddGroup.class, EditGroup.class})
  private String remark;

  /**
   * 删除人ID
   */
  @NotNull(message = "删除人ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long deleteBy;

  /**
   * 删除人
   */
  @NotBlank(message = "删除人不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deleteByName;


}

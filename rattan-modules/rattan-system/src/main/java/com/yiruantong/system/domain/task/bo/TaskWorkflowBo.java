package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.TaskWorkflow;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.util.Date;


/**
 * 业务工作流关联设置业务对象 task_workflow
 *
 * @author YRT
 * @date 2024-07-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskWorkflow.class, reverseConvertGenerate = false)
public class TaskWorkflowBo extends BaseEntity {

  /**
   * 行ID
   */
  @NotNull(message = "行ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long rowId;

  /**
   * 单据ID
   */
  @NotNull(message = "单据ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 单据号
   */
  @NotBlank(message = "单据号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 审核流部署ID
   */
  @NotBlank(message = "审核流部署ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deployId;

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
   * 模块ID
   */
  @NotNull(message = "模块ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long menuId;

  /**
   * 模块名称
   */
  @NotBlank(message = "模块名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String menuName;


}

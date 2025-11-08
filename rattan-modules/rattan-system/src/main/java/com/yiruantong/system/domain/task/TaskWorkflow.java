package com.yiruantong.system.domain.task;

import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 业务工作流关联设置对象 task_workflow
 *
 * @author YRT
 * @date 2024-07-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_workflow", autoResultMap = true)
public class TaskWorkflow extends TenantEntity {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 行ID
   */
  @TableId(value = "row_id")
  private Long rowId;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 单据号
   */
  private String billCode;

  /**
   * 审核流部署ID
   */
  private String deployId;

  /**
   * 备注
   */
  private String remark;

  /**
   * 删除时间
   */
  private Date deleteTime;

  /**
   * 删除人id
   */
  private Long deleteBy;

  /**
   * 删除人
   */
  private String deleteByName;

  /**
   * 模块ID
   */
  private Long menuId;

  /**
   * 模块名称
   */
  private String menuName;


}

package com.yiruantong.basic.domain.common;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;
import java.util.Map;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 业务操作日志对象 common_operation_log
 *
 * @author YRT
 * @date 2025-03-15
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "common_operation_log", autoResultMap = true)
public class CommonOperationLog extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 历史ID
   */
    @TableId(value = "operation_id")
  private Long operationId;

  /**
   * 单据类型
   */
  private String billType;

  /**
   * 单据id
   */
  private Long billId;

  /**
   * 单号
   */
  private String billCode;

  /**
   * 状态类型
   */
  private String statusType;

  /**
   * 操作类型
   */
  private String operationType;

  /**
   * 变更前状态
   */
  private String fromStatus;

  /**
   * 变更后状态
   */
  private String toStatus;

  /**
   * 状态级别
   */
  private String statusLevel;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 扩展字段
   */
    @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

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


}

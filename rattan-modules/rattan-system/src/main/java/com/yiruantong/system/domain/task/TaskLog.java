package com.yiruantong.system.domain.task;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 任务日志对象 task_log
 *
 * @author YRT
 * @date 2024-12-16
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_log", autoResultMap = true)
public class TaskLog extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 日志ID
   */
    @TableId(value = "log_id")
  private Long logId;

  /**
   * 配置ID
   */
  private Long configId;

  /**
   * 平台名称
   */
  private String platName;

  /**
   * 模块名称
   */
  private String moduleName;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 单号
   */
  private String billCode;

  /**
   * 推送类型
   */
  private String pushType;

  /**
   * 推送次数
   */
  private Long pushCount;

  /**
   * 推送时间
   */
  private Date pushDate;

  /**
   * 推送状态
   */
  private String pushState;

  /**
   * 推送消息
   */
  private String resultMsg;

  /**
   * 推送结果
   */
  private String resultJson;

  /**
   * 异常消息
   */
  private String exceptionMsg;

  /**
   * 推送数据
   */
  private String pushData;

  /**
   * 扩展字段
   */
    @TableField(value = "expand_fields", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> expandFields;

  /**
   * 备注
   */
  private String remark;


}

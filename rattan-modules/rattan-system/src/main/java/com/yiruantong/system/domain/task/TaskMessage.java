package com.yiruantong.system.domain.task;

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
 * 消息推送对象 task_message
 *
 * @author YRT
 * @date 2025-03-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_message", autoResultMap = true)
public class TaskMessage extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 消息ID
   */
    @TableId(value = "message_id")
  private Long messageId;

  /**
   * 平台标识
   */
  private String platform;

  /**
   * 消息类型
   */
  private String msgType;

  /**
   * 消息内容
   */
  private String content;

  /**
   * 接收者信息
   */
  private String receiver;

  /**
   * 发送状态
   */
  private String status;

  /**
   * 重试次数
   */
  private Long retryCount;

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


}

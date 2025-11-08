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
 * 消息推送配置对象 task_message_config
 *
 * @author YRT
 * @date 2025-03-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_message_config", autoResultMap = true)
public class TaskMessageConfig extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 推送配置ID
   */
    @TableId(value = "message_config_id")
  private Long messageConfigId;

  /**
   * 平台标识
   */
  private String platform;

  /**
   * appKey
   */
  private String appKey;

  /**
   * appSecret
   */
  private String appSecret;

  /**
   * 平台认证Token
   */
  private String token;

  /**
   * Token过期时间
   */
  private String tokenExpireAt;

  /**
   * 平台API地址
   */
  private String apiUrl;

  /**
   * 消息模板
   */
  private String msgTemplate;

  /**
   * 重试策略
   */
    @TableField(value = "retry_policy", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> retryPolicy;

  /**
   * 回调URL
   */
  private String callbackUrl;

  /**
   * 配置状态
   */
  private Byte enable;

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
   * 父级ID
   */
  private Long parentId;

  /**
   * 排序号
   */
  private Long orderNum;


}

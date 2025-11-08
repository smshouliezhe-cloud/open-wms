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
 * 任务配置对象 task_config
 *
 * @author YRT
 * @date 2024-12-19
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_config", autoResultMap = true)
public class TaskConfig extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 任务配置ID
   */
    @TableId(value = "config_id")
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
   * 接口名称
   */
  private String apiName;

  /**
   * 任务类型
   */
  private String taskType;

  /**
   * 任务开始日期
   */
  private Date startDate;

  /**
   * 任务结束日期
   */
  private Date endDate;

  /**
   * 任务开始时间
   */
  private String startTime;

  /**
   * 任务结束时间
   */
  private String endTime;

  /**
   * 是否启用
   */
  private Byte enable;

  /**
   * 推送条件
   */
    @TableField(value = "pushWhere", typeHandler = JacksonTypeHandler.class)
  private Map<String, Object> pushWhere;

  /**
   * 查询接口
   */
  private String prefixRouter;

  /**
   * 菜单ID
   */
  private Long menuId;

  /**
   * 最大推送次数
   */
  private Long maxPushCount;

  /**
   * 推送接口地址
   */
  private String pushUrl;

  /**
   * 推送接口方法
   */
  private String method;

  /**
   * appKey
   */
  private String appKey;

  /**
   * appSecrect
   */
  private String appSecrect;

  /**
   * grantType
   */
  private String grantType;

  /**
   * 状态值
   */
  private String code;

  /**
   * 最后执行结果
   */
  private String lastRunMsg;

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
   * 来源类别
   */
  private String sourceType;

  /**
   * 来源ID
   */
  private String sourceId;

  /**
   * 来源单号
   */
  private String sourceCode;

  /**
   * ID字段名
   */
  private String idField;

  /**
   * Code字段名
   */
  private String codeField;

  /**
   * 排序字段
   */
  private String orderbyField;

  /**
   * 排序方式
   */
  private String orderbyType;

  /**
   * 父级ID
   */
  private Long parentId;

  /**
   * 排序号
   */
  private Long orderNum;

  /**
   * 状态字段
   */
  private String statusField;

  /**
   * 手动推送地址
   */
  private String pushApi;


}

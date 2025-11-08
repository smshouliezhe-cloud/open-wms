package com.yiruantong.system.domain.task;

  import com.yiruantong.common.mybatis.core.domain.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.Map;
  import java.math.BigDecimal;
  import java.util.Date;
  import com.fasterxml.jackson.annotation.JsonFormat;


import java.io.Serial;

/**
 * 消息队列对象 task_queue
 *
 * @author YRT
 * @date 2024-12-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "task_queue", autoResultMap = true)
public class TaskQueue extends TenantEntity {

@Serial
private static final long serialVersionUID=1L;

  /**
   * 队列ID
   */
    @TableId(value = "task_id")
  private Long taskId;

  /**
   * 队列类型
   */
  private String taskType;

  /**
   * 单据ID
   */
  private Long billId;

  /**
   * 单据编号
   */
  private String billCode;

  /**
   * 队列状态
   */
  private String taskStatus;

  /**
   * 输送线id
   */
  private Long tranId;

  /**
   * 输送线编号
   */
  private String tranCode;

  /**
   * 输送线状态
   */
  private String tranStatus;

  /**
   * RgvID
   */
  private Long rgvId;

  /**
   * Rgv编号
   */
  private String rgvCode;

  /**
   * Rgv状态
   */
  private String rgvStatus;

  /**
   * 码垛机ID
   */
  private Long srmId;

  /**
   * 码垛机编号
   */
  private String srmCode;

  /**
   * 码垛机状态
   */
  private String srmStatus;

  /**
   * 是否可用
   */
  private Long enable;

  /**
   * 执行次数
   */
  private Long doCount;

  /**
   * 是否正在执行
   */
  private Long isDoing;

  /**
   * 关联任务ID
   */
  private Long relationTaskId;

  /**
   * 源位
   */
  private String fromPositionName;

  /**
   * 目标位
   */
  private String toPositionName;

  /**
   * 仓库ID
   */
  private Long storageId;

  /**
   * 仓库名称
   */
  private String storageName;

  /**
   * 器具种类
   */
  private String plateType;

  /**
   * 容器号
   */
  private String plateCode;

  /**
   * 方向
   */
  private String direction;

  /**
   * 权重
   */
  private Long orderNumber;

  /**
   * ID
   */
  private Long productPositionId;

  /**
   * 任务开始时间
   */
  private Date beginDate;

  /**
   * 任务结束时间
   */
  private Date endDate;

  /**
   * 任务时长
   */
  private BigDecimal timeLength;

  /**
   * 分拨口类型
   */
  private String regionType;

  /**
   * 分拨口
   */
  private String portName;

  /**
   * 数据
   */
  private String jsonData;

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
   * 来源单号
   */
  private String sourceCode;

  /**
   * 来源单号Id
   */
  private String sourceId;

  /**
   * 其他参数
   */
  private String otherField;

  /**
   * 模块ID
   */
  private Long menuId;

  /**
   * 模块名称
   */
  private String menuName;

  /**
   * 执行动作
   */
  private String action;

  /**
   * 流程节点名称
   */
  private String taskName;

  /**
   * 流程部署ID
   */
  private String deployId;

  /**
   * 队列类型编号
   */
  private String taskTypeCode;

  /**
   * 根单据ID
   */
  private Long rootId;

  /**
   * 根单据编号
   */
  private String rootCode;


}

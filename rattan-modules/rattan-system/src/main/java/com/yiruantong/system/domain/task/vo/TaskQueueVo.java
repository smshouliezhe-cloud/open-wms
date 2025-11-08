package com.yiruantong.system.domain.task.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import com.yiruantong.system.domain.task.TaskQueue;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 消息队列视图对象 task_queue
 *
 * @author YRT
 * @date 2024-12-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskQueue.class)
public class TaskQueueVo implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * 队列ID
   */
  @ExcelProperty(value = "队列ID")
  private Long taskId;

  /**
   * 队列类型
   */
  @ExcelProperty(value = "队列类型")
  private String taskType;

  /**
   * 单据ID
   */
  @ExcelProperty(value = "单据ID")
  private Long billId;

  /**
   * 单据编号
   */
  @ExcelProperty(value = "单据编号")
  private String billCode;

  /**
   * 队列状态
   */
  @ExcelProperty(value = "队列状态")
  private String taskStatus;

  /**
   * 输送线id
   */
  @ExcelProperty(value = "输送线id")
  private Long tranId;

  /**
   * 输送线编号
   */
  @ExcelProperty(value = "输送线编号")
  private String tranCode;

  /**
   * 输送线状态
   */
  @ExcelProperty(value = "输送线状态")
  private String tranStatus;

  /**
   * RgvID
   */
  @ExcelProperty(value = "RgvID")
  private Long rgvId;

  /**
   * Rgv编号
   */
  @ExcelProperty(value = "Rgv编号")
  private String rgvCode;

  /**
   * Rgv状态
   */
  @ExcelProperty(value = "Rgv状态")
  private String rgvStatus;

  /**
   * 码垛机ID
   */
  @ExcelProperty(value = "码垛机ID")
  private Long srmId;

  /**
   * 码垛机编号
   */
  @ExcelProperty(value = "码垛机编号")
  private String srmCode;

  /**
   * 码垛机状态
   */
  @ExcelProperty(value = "码垛机状态")
  private String srmStatus;

  /**
   * 是否可用
   */
  @ExcelProperty(value = "是否可用")
  private Long enable;

  /**
   * 执行次数
   */
  @ExcelProperty(value = "执行次数")
  private Long doCount;

  /**
   * 是否正在执行
   */
  @ExcelProperty(value = "是否正在执行")
  private Long isDoing;

  /**
   * 关联任务ID
   */
  @ExcelProperty(value = "关联任务ID")
  private Long relationTaskId;

  /**
   * 源位
   */
  @ExcelProperty(value = "源位")
  private String fromPositionName;

  /**
   * 目标位
   */
  @ExcelProperty(value = "目标位")
  private String toPositionName;

  /**
   * 仓库ID
   */
  @ExcelProperty(value = "仓库ID")
  private Long storageId;

  /**
   * 仓库名称
   */
  @ExcelProperty(value = "仓库名称")
  private String storageName;

  /**
   * 器具种类
   */
  @ExcelProperty(value = "器具种类")
  private String plateType;

  /**
   * 容器号
   */
  @ExcelProperty(value = "容器号")
  private String plateCode;

  /**
   * 方向
   */
  @ExcelProperty(value = "方向")
  private String direction;

  /**
   * 权重
   */
  @ExcelProperty(value = "权重")
  private Long orderNumber;

  /**
   * ID
   */
  @ExcelProperty(value = "ID")
  private Long productPositionId;

  /**
   * 任务开始时间
   */
  @ExcelProperty(value = "任务开始时间")
  private Date beginDate;

  /**
   * 任务结束时间
   */
  @ExcelProperty(value = "任务结束时间")
  private Date endDate;

  /**
   * 任务时长
   */
  @ExcelProperty(value = "任务时长")
  private BigDecimal timeLength;

  /**
   * 分拨口类型
   */
  @ExcelProperty(value = "分拨口类型")
  private String regionType;

  /**
   * 分拨口
   */
  @ExcelProperty(value = "分拨口")
  private String portName;

  /**
   * 数据
   */
  @ExcelProperty(value = "数据")
  private String jsonData;

  /**
   * 备注
   */
  @ExcelProperty(value = "备注")
  private String remark;

  /**
   * 创建人
   */
  @ExcelProperty(value = "创建人")
  private String createByName;

  /**
   * 创建时间
   */
  @ExcelProperty(value = "创建时间")
  private Date createTime;

  /**
   * 修改人
   */
  @ExcelProperty(value = "修改人")
  private String updateByName;

  /**
   * 修改时间
   */
  @ExcelProperty(value = "修改时间")
  private Date updateTime;

  /**
   * 删除时间
   */
  @ExcelProperty(value = "删除时间")
  private Date deleteTime;

  /**
   * 删除人id
   */
  @ExcelProperty(value = "删除人id")
  private Long deleteBy;

  /**
   * 删除人
   */
  @ExcelProperty(value = "删除人")
  private String deleteByName;

  /**
   * 来源单号
   */
  @ExcelProperty(value = "来源单号")
  private String sourceCode;

  /**
   * 来源单号Id
   */
  @ExcelProperty(value = "来源单号Id")
  private String sourceId;

  /**
   * 其他参数
   */
  @ExcelProperty(value = "其他参数")
  private String otherField;

  /**
   * 模块ID
   */
  @ExcelProperty(value = "模块ID")
  private Long menuId;

  /**
   * 模块名称
   */
  @ExcelProperty(value = "模块名称")
  private String menuName;

  /**
   * 执行动作
   */
  @ExcelProperty(value = "执行动作")
  private String action;

  /**
   * 流程节点名称
   */
  @ExcelProperty(value = "流程节点名称")
  private String taskName;

  /**
   * 流程部署ID
   */
  @ExcelProperty(value = "流程部署ID")
  private String deployId;

  /**
   * 队列类型编号
   */
  @ExcelProperty(value = "队列类型编号")
  private String taskTypeCode;

  /**
   * 根单据ID
   */
  @ExcelProperty(value = "根单据ID")
  private Long rootId;

  /**
   * 根单据编号
   */
  @ExcelProperty(value = "根单据编号")
  private String rootCode;


}

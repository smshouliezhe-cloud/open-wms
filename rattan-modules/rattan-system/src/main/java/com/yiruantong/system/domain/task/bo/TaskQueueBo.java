package com.yiruantong.system.domain.task.bo;

import com.yiruantong.system.domain.task.TaskQueue;
import com.yiruantong.common.mybatis.core.domain.BaseEntity;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 消息队列业务对象 task_queue
 *
 * @author YRT
 * @date 2024-12-13
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskQueue.class, reverseConvertGenerate = false)
public class TaskQueueBo extends BaseEntity {

  /**
   * 队列ID
   */
  @NotNull(message = "队列ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long taskId;

  /**
   * 队列类型
   */
  @NotBlank(message = "队列类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String taskType;

  /**
   * 单据ID
   */
  @NotNull(message = "单据ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long billId;

  /**
   * 单据编号
   */
  @NotBlank(message = "单据编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String billCode;

  /**
   * 队列状态
   */
  @NotBlank(message = "队列状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String taskStatus;

  /**
   * 输送线id
   */
  @NotNull(message = "输送线id不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long tranId;

  /**
   * 输送线编号
   */
  @NotBlank(message = "输送线编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tranCode;

  /**
   * 输送线状态
   */
  @NotBlank(message = "输送线状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String tranStatus;

  /**
   * RgvID
   */
  @NotNull(message = "RgvID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long rgvId;

  /**
   * Rgv编号
   */
  @NotBlank(message = "Rgv编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String rgvCode;

  /**
   * Rgv状态
   */
  @NotBlank(message = "Rgv状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String rgvStatus;

  /**
   * 码垛机ID
   */
  @NotNull(message = "码垛机ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long srmId;

  /**
   * 码垛机编号
   */
  @NotBlank(message = "码垛机编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String srmCode;

  /**
   * 码垛机状态
   */
  @NotBlank(message = "码垛机状态不能为空", groups = {AddGroup.class, EditGroup.class})
  private String srmStatus;

  /**
   * 是否可用
   */
  @NotNull(message = "是否可用不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long enable;

  /**
   * 执行次数
   */
  @NotNull(message = "执行次数不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long doCount;

  /**
   * 是否正在执行
   */
  @NotNull(message = "是否正在执行不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long isDoing;

  /**
   * 关联任务ID
   */
  @NotNull(message = "关联任务ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long relationTaskId;

  /**
   * 源位
   */
  @NotBlank(message = "源位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String fromPositionName;

  /**
   * 目标位
   */
  @NotBlank(message = "目标位不能为空", groups = {AddGroup.class, EditGroup.class})
  private String toPositionName;

  /**
   * 仓库ID
   */
  @NotNull(message = "仓库ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long storageId;

  /**
   * 仓库名称
   */
  @NotBlank(message = "仓库名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String storageName;

  /**
   * 器具种类
   */
  @NotBlank(message = "器具种类不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateType;

  /**
   * 容器号
   */
  @NotBlank(message = "容器号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String plateCode;

  /**
   * 方向
   */
  @NotBlank(message = "方向不能为空", groups = {AddGroup.class, EditGroup.class})
  private String direction;

  /**
   * 权重
   */
  @NotNull(message = "权重不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long orderNumber;

  /**
   * ID
   */
  @NotNull(message = "ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long productPositionId;

  /**
   * 任务开始时间
   */
  @NotNull(message = "任务开始时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date beginDate;

  /**
   * 任务结束时间
   */
  @NotNull(message = "任务结束时间不能为空", groups = {AddGroup.class, EditGroup.class})
  private Date endDate;

  /**
   * 任务时长
   */
  @NotNull(message = "任务时长不能为空", groups = {AddGroup.class, EditGroup.class})
  private BigDecimal timeLength;

  /**
   * 分拨口类型
   */
  @NotBlank(message = "分拨口类型不能为空", groups = {AddGroup.class, EditGroup.class})
  private String regionType;

  /**
   * 分拨口
   */
  @NotBlank(message = "分拨口不能为空", groups = {AddGroup.class, EditGroup.class})
  private String portName;

  /**
   * 数据
   */
  @NotBlank(message = "数据不能为空", groups = {AddGroup.class, EditGroup.class})
  private String jsonData;

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
   * 来源单号
   */
  @NotBlank(message = "来源单号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceCode;

  /**
   * 来源单号Id
   */
  @NotBlank(message = "来源单号Id不能为空", groups = {AddGroup.class, EditGroup.class})
  private String sourceId;

  /**
   * 其他参数
   */
  @NotBlank(message = "其他参数不能为空", groups = {AddGroup.class, EditGroup.class})
  private String otherField;

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

  /**
   * 执行动作
   */
  @NotBlank(message = "执行动作不能为空", groups = {AddGroup.class, EditGroup.class})
  private String action;

  /**
   * 流程节点名称
   */
  @NotBlank(message = "流程节点名称不能为空", groups = {AddGroup.class, EditGroup.class})
  private String taskName;

  /**
   * 流程部署ID
   */
  @NotBlank(message = "流程部署ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private String deployId;

  /**
   * 队列类型编号
   */
  @NotBlank(message = "队列类型编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String taskTypeCode;

  /**
   * 根单据ID
   */
  @NotNull(message = "根单据ID不能为空", groups = {AddGroup.class, EditGroup.class})
  private Long rootId;

  /**
   * 根单据编号
   */
  @NotBlank(message = "根单据编号不能为空", groups = {AddGroup.class, EditGroup.class})
  private String rootCode;


}

package com.yiruantong.system.service.task;

import com.yiruantong.system.domain.task.TaskQueue;
import com.yiruantong.system.domain.task.bo.TaskQueueBo;
import com.yiruantong.system.domain.task.vo.TaskQueueVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;

import java.util.Map;

/**
 * 消息队列Service接口
 *
 * @author YRT
 * @date 2024-01-05
 */
public interface ITaskQueueService extends IServicePlus<TaskQueue, TaskQueueVo, TaskQueueBo> {
  /**
   * 创建消息任务
   *
   * @param rabbitReceiverDto 接收器Dto
   * @return R
   */
  R<Void> createTask(RabbitReceiverDto rabbitReceiverDto);

  /**
   * 更新任务状态
   *
   * @param taskId              任务ID
   * @param taskQueueStatusEnum 任务状态
   */
  void updateStatus(Long taskId, TaskQueueStatusEnum taskQueueStatusEnum);

  /**
   * 更新任务状态
   *
   * @param taskId              任务ID
   * @param taskQueueStatusEnum 任务状态
   */
  void updateStatus(Long taskId, TaskQueueStatusEnum taskQueueStatusEnum, String Remark);

  /**
   * 重新执行消息队列
   *
   * @param map 前端传入数据
   * @return R
   */
  R<Void> push(Map<String, Object> map);

  /**
   * 判断消息队列是否执行成功
   *
   * @param taskId 队列Id
   * @return boolean
   */
  Boolean checkTaskFinished(Long taskId);
}

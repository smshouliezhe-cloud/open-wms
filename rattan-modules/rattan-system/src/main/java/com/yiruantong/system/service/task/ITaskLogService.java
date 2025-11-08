package com.yiruantong.system.service.task;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.task.TaskLog;
import com.yiruantong.system.domain.task.bo.TaskLogBo;
import com.yiruantong.system.domain.task.vo.TaskLogVo;

import java.util.List;

/**
 * 任务日志Service接口
 *
 * @author YRT
 * @date 2024-12-16
 */
public interface ITaskLogService extends IServicePlus<TaskLog, TaskLogVo, TaskLogBo> {
  /**
   * 根据配置ID，单据ID获取数据
   *
   * @param configId 配置ID
   * @param billId   单据ID
   * @return TaskLog
   */
  TaskLog findOne(Long configId, Long billId);

  /**
   * 添加成功日志
   *
   * @param configId  配置ID
   * @param billId    单据ID
   * @param billCode  单据编号
   * @param resultMsg 推送结果消息
   */
  void addSuccess(Long configId, Long billId, String billCode, String resultMsg);

  /**
   * 添加失败日志
   *
   * @param configId     配置ID
   * @param billId       单据ID
   * @param billCode     单据编号
   * @param exceptionMsg 推送异常消息
   */
  void addFail(Long configId, Long billId, String billCode, String exceptionMsg);

  /**
   * 根据配置ID，单据ID获取未推送的ID集合
   *
   * @param configId   配置ID
   * @param billIdList 单据ID集合
   * @return TaskLog
   */
  List<Long> findNotPushIdList(Long configId, List<Long> billIdList);

}

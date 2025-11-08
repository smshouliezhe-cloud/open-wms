package com.yiruantong.system.service.task.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.common.core.enums.system.PushStatusEnum;
import com.yiruantong.common.core.utils.B;
import com.yiruantong.common.core.utils.SpringUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.system.domain.task.TaskConfig;
import com.yiruantong.system.domain.task.TaskLog;
import com.yiruantong.system.domain.task.bo.TaskLogBo;
import com.yiruantong.system.domain.task.vo.TaskLogVo;
import com.yiruantong.system.mapper.task.TaskLogMapper;
import com.yiruantong.system.service.task.ITaskConfigService;
import com.yiruantong.system.service.task.ITaskLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务日志Service业务层处理
 *
 * @author YRT
 * @date 2024-12-16
 */
@RequiredArgsConstructor
@Service
public class TaskLogServiceImpl extends ServiceImplPlus<TaskLogMapper, TaskLog, TaskLogVo, TaskLogBo> implements ITaskLogService {
  @Override
  public TaskLog findOne(Long configId, Long billId) {
    LambdaUpdateWrapper<TaskLog> taskLogLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    taskLogLambdaUpdateWrapper
      .eq(TaskLog::getConfigId, configId)
      .eq(TaskLog::getBillId, billId);
    return this.getOne(taskLogLambdaUpdateWrapper);
  }

  @Override
  public void addSuccess(Long configId, Long billId, String billCode, String resultMsg) {
    ITaskConfigService taskConfigService = SpringUtils.getBean(ITaskConfigService.class);
    TaskConfig taskConfig = taskConfigService.getById(configId);
    TaskLog taskLog = this.findOne(configId, billId);
    if (ObjectUtil.isNull(taskLog)) {
      taskLog = new TaskLog();
      taskLog.setPushCount(0L);
    }
    taskLog.setBillId(billId);
    taskLog.setBillCode(billCode);
    taskLog.setExceptionMsg(null);
    taskLog.setConfigId(taskConfig.getConfigId());
    taskLog.setModuleName(taskConfig.getModuleName());
    taskLog.setPlatName(taskConfig.getPlatName());
    taskLog.setPushDate(DateUtil.date());
    taskLog.setPushState(PushStatusEnum.SUCCESS.getName());
    taskLog.setPushCount(taskLog.getPushCount() + 1L);
    taskLog.setPushData(null);
    taskLog.setResultMsg(resultMsg);
    this.saveOrUpdate(taskLog);
  }

  @Override
  public void addFail(Long configId, Long billId, String billCode, String exceptionMsg) {
    ITaskConfigService taskConfigService = SpringUtils.getBean(ITaskConfigService.class);
    TaskConfig taskConfig = taskConfigService.getById(configId);
    TaskLog taskLog = this.findOne(configId, billId);
    if (ObjectUtil.isNull(taskLog)) {
      taskLog = new TaskLog();
      taskLog.setPushCount(0L);
    }
    taskLog.setBillId(billId);
    taskLog.setBillCode(billCode);
    taskLog.setExceptionMsg(null);
    taskLog.setConfigId(taskConfig.getConfigId());
    taskLog.setModuleName(taskConfig.getModuleName());
    taskLog.setPlatName(taskConfig.getPlatName());
    taskLog.setPushDate(DateUtil.date());
    taskLog.setPushState(PushStatusEnum.FAILED.getName());
    taskLog.setPushCount(taskLog.getPushCount() + 1L);
    taskLog.setPushData(null);
    taskLog.setResultMsg(null);
    taskLog.setResultMsg(exceptionMsg);

    this.saveOrUpdate(taskLog);
  }

  @Override
  public List<Long> findNotPushIdList(Long configId, List<Long> billIdList) {
    LambdaUpdateWrapper<TaskLog> taskLogLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
    taskLogLambdaUpdateWrapper
      .eq(TaskLog::getConfigId, configId)
      .in(TaskLog::getBillId, billIdList);
    final List<TaskLogVo> taskLogVos = this.selectList(taskLogLambdaUpdateWrapper);
    return billIdList.stream().filter(billId -> taskLogVos.stream().noneMatch(t -> B.isEqual(t.getBillId(), billId))).toList();
  }
}

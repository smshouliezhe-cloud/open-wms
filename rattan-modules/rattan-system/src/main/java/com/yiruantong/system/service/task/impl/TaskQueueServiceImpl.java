package com.yiruantong.system.service.task.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.yiruantong.system.domain.task.TaskQueue;
import com.yiruantong.system.domain.task.bo.TaskQueueBo;
import com.yiruantong.system.domain.task.vo.TaskQueueVo;
import com.yiruantong.system.mapper.task.TaskQueueMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.domain.model.LoginUser;
import com.yiruantong.common.core.enums.system.RabbitmqTypeEnum;
import com.yiruantong.common.core.enums.system.TaskQueueStatusEnum;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.NumberUtils;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.json.utils.JsonUtils;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.common.rabbitmq.domain.DoStepDto;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;
import com.yiruantong.common.rabbitmq.service.IMqStepService;
import com.yiruantong.common.rabbitmq.service.IRabbitProducerService;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.common.tenant.helper.TenantHelper;
import com.yiruantong.system.service.task.ITaskQueueService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;

/**
 * 消息队列Service业务层处理
 *
 * @author YRT
 * @date 2024-01-05
 */
@RequiredArgsConstructor
@Service
public class TaskQueueServiceImpl extends ServiceImplPlus<TaskQueueMapper, TaskQueue, TaskQueueVo, TaskQueueBo> implements ITaskQueueService, IMqStepService {
  private final IRabbitProducerService rabbitProducerService;

  @Override
  public R<Void> createTask(RabbitReceiverDto rabbitReceiverDto) {
    LoginUser loginUser = LoginHelper.getLoginUser();
    if (ObjectUtil.isNull(loginUser)) {
      loginUser = rabbitReceiverDto.getLoginUser();
    }
    Assert.isFalse(ObjectUtil.isNull(loginUser), "创建消息队列时，当前用户信息为空");

    // 校验数据
    if (ObjectUtil.isEmpty(rabbitReceiverDto) || ObjectUtil.isNull(rabbitReceiverDto.getBillId()) || StrUtil.isEmpty(rabbitReceiverDto.getBillCode())) {
      throw new ServiceException("消息队列类型、单据ID、单据编号为空,消息队列执行失败");
    }
//    LambdaQueryWrapper<TaskQueue> queryWrapper = new LambdaQueryWrapper<>();
//    queryWrapper.eq(TaskQueue::getRootId, rabbitReceiverDto.getRootId())
//      .eq(TaskQueue::getTaskTypeCode, rabbitReceiverDto.getRabbitmqType().toString());
//    if (this.exists(queryWrapper)) {
//      throw new ServiceException(rabbitReceiverDto.getRabbitmqType() + "已存在，不可重复执行！");
//    }

    TaskQueue taskQueue = new TaskQueue();
    taskQueue.setBeginDate(DateUtil.date());
    taskQueue.setTaskType(rabbitReceiverDto.getRabbitmqType().getName());
    taskQueue.setTaskTypeCode(rabbitReceiverDto.getRabbitmqType().toString());
    taskQueue.setRootId(NumberUtils.ifNullDefault(rabbitReceiverDto.getRootId(), rabbitReceiverDto.getBillId()));
    taskQueue.setRootCode(StringUtils.defaultIfBlank(rabbitReceiverDto.getRootCode(), rabbitReceiverDto.getBillCode()));
    taskQueue.setBillId(rabbitReceiverDto.getBillId());
    taskQueue.setBillCode(rabbitReceiverDto.getBillCode());

    taskQueue.setCreateBy(loginUser.getUserId());
    taskQueue.setCreateByName(loginUser.getNickname());
    taskQueue.setCreateTime(DateUtil.date());
    taskQueue.setDoCount(1L);
    taskQueue.setTaskStatus(TaskQueueStatusEnum.AWAITING_IMPLEMENTATION.getName());
    taskQueue.setSourceCode(rabbitReceiverDto.getSourceCode());
    taskQueue.setSourceId(rabbitReceiverDto.getSourceId());
    taskQueue.setOtherField(JsonUtils.toJsonString(rabbitReceiverDto.getOtherField()));
    taskQueue.setMenuId(rabbitReceiverDto.getMenuId());
    taskQueue.setMenuName(rabbitReceiverDto.getMenuName());
    if (ObjectUtil.isNotEmpty(rabbitReceiverDto.getAction())) {
      taskQueue.setAction(rabbitReceiverDto.getAction().getName());
    }
    taskQueue.setTaskName(rabbitReceiverDto.getTaskName());
    taskQueue.setDeployId(rabbitReceiverDto.getDeployId());
    taskQueue.setJsonData(JsonUtils.toJsonString(rabbitReceiverDto));
    this.save(taskQueue);
    rabbitReceiverDto.setTaskId(taskQueue.getTaskId());
    rabbitReceiverDto.setCreateDate(DateUtil.date());
    taskQueue.setJsonData(JsonUtils.toJsonString(rabbitReceiverDto));
    this.updateById(taskQueue);

    // 事务完成后在执行推送消息
    if (TransactionSynchronizationManager.isActualTransactionActive()) {
      // 当前存在事务
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
        @Override
        public void afterCommit() {
          rabbitProducerService.sendMsg(rabbitReceiverDto);
        }
      });
    } else {
      // 当前不存在事务
      rabbitProducerService.sendMsg(rabbitReceiverDto);
    }

    return R.ok();
  }

  @Override
  public void updateStatus(Long taskId, TaskQueueStatusEnum taskQueueStatusEnum) {
    this.updateStatus(taskId, taskQueueStatusEnum, "执行成功");
  }

  @Override
  public void updateStatus(Long taskId, TaskQueueStatusEnum taskQueueStatusEnum, String Remark) {
    LambdaUpdateWrapper<TaskQueue> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(TaskQueue::getTaskStatus, taskQueueStatusEnum.getName())
      .set(TaskQueue::getEndDate, DateUtil.date())
      .setSql("time_length=TIMESTAMPDIFF(HOUR,begin_date,NOW())")
      .set(TaskQueue::getRemark, Remark)
      .eq(TaskQueue::getTaskId, taskId);
    this.update(updateWrapper);
  }

  /**
   * 重新执行消息队列
   *
   * @param map 前端传入数据
   * @return
   */
  @Override
  public R<Void> push(Map<String, Object> map) {
    RabbitmqTypeEnum rabbitmqTypeEnum = RabbitmqTypeEnum.matchingEnum(Convert.toStr(map.get("taskType")));
    Assert.isTrue(ObjectUtil.isNotEmpty(rabbitmqTypeEnum), "未找到对应的队列类型");

    TaskQueue taskQueue = this.getById(Convert.toLong(map.get("taskId")));
    taskQueue.setDoCount(taskQueue.getDoCount() + 1);
    this.updateById(taskQueue);

    RabbitReceiverDto rabbitReceiverDto = JsonUtils.parseObject(taskQueue.getJsonData(), RabbitReceiverDto.class);
    // 推送消息
    rabbitProducerService.sendMsg(rabbitReceiverDto);

    return R.ok("执行完成");
  }

  @Override
  public Boolean checkTaskFinished(Long taskId) {
    TaskQueue taskQueue = this.getById(taskId);
    return ObjectUtil.isNotEmpty(taskQueue) && StrUtil.equals(taskQueue.getTaskStatus(), TaskQueueStatusEnum.IMPLEMENTATION_COMPLETED.getName());
  }

  /**
   * 执行下一步消息队列
   *
   * @param rabbitReceiverDto 事件
   */
  @Override
  public void doNextStepMQ(RabbitReceiverDto rabbitReceiverDto) {
    if (ObjectUtil.isNull(rabbitReceiverDto) || ObjectUtil.isNull(rabbitReceiverDto.getDoStepList())) {
      return;
    }
    LoginHelper.setLoginUser(rabbitReceiverDto.getLoginUser());
    TenantHelper.setDynamic(LoginHelper.getTenantId());

    // 执行下一步消息队列
    for (DoStepDto step : rabbitReceiverDto.getDoStepList()) {
      if (!step.isFinished()) {
        step.setFinished(true); // 标记为完成
        rabbitReceiverDto.setRabbitmqType(step.getRabbitmqType()); // 下一步消息队列
        rabbitReceiverDto.setSourceId(rabbitReceiverDto.getBillId().toString());
        rabbitReceiverDto.setSourceCode(rabbitReceiverDto.getBillCode());
        rabbitReceiverDto.setBillId(step.getBillId());
        rabbitReceiverDto.setBillCode(step.getBillCode());
        rabbitReceiverDto.setSourceId(step.getSourceId());
        rabbitReceiverDto.setSourceCode(step.getSourceCode());
        rabbitReceiverDto.setOtherField(step.getOtherField());
        // 创建消息队列
        this.createTask(rabbitReceiverDto);
        break;
      }
    }
  }
  //#endregion
}

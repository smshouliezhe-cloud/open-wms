package com.yiruantong.system.service.task;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.task.TaskMessage;
import com.yiruantong.system.domain.task.vo.TaskMessageVo;
import com.yiruantong.system.domain.task.bo.TaskMessageBo;

/**
 * 消息推送Service接口
 *
 * @author YRT
 * @date 2025-03-23
 */
public interface ITaskMessageService extends IServicePlus<TaskMessage, TaskMessageVo, TaskMessageBo> {
}

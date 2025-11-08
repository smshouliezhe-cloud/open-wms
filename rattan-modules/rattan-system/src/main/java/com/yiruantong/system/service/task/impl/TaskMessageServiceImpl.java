package com.yiruantong.system.service.task.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.system.domain.task.bo.TaskMessageBo;
import com.yiruantong.system.domain.task.vo.TaskMessageVo;
import com.yiruantong.system.domain.task.TaskMessage;
import com.yiruantong.system.mapper.task.TaskMessageMapper;
import com.yiruantong.system.service.task.ITaskMessageService;

/**
 * 消息推送Service业务层处理
 *
 * @author YRT
 * @date 2025-03-23
 */
@RequiredArgsConstructor
@Service
public class TaskMessageServiceImpl extends ServiceImplPlus<TaskMessageMapper, TaskMessage, TaskMessageVo, TaskMessageBo> implements ITaskMessageService {
}

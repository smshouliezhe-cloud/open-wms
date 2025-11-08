package com.yiruantong.system.service.task.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.system.domain.task.bo.TaskMessageConfigBo;
import com.yiruantong.system.domain.task.vo.TaskMessageConfigVo;
import com.yiruantong.system.domain.task.TaskMessageConfig;
import com.yiruantong.system.mapper.task.TaskMessageConfigMapper;
import com.yiruantong.system.service.task.ITaskMessageConfigService;

/**
 * 消息推送配置Service业务层处理
 *
 * @author YRT
 * @date 2025-03-23
 */
@RequiredArgsConstructor
@Service
public class TaskMessageConfigServiceImpl extends ServiceImplPlus<TaskMessageConfigMapper, TaskMessageConfig, TaskMessageConfigVo, TaskMessageConfigBo> implements ITaskMessageConfigService {
}

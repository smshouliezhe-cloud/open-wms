package com.yiruantong.system.service.task;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.system.domain.task.TaskMessageConfig;
import com.yiruantong.system.domain.task.vo.TaskMessageConfigVo;
import com.yiruantong.system.domain.task.bo.TaskMessageConfigBo;

/**
 * 消息推送配置Service接口
 *
 * @author YRT
 * @date 2025-03-23
 */
public interface ITaskMessageConfigService extends IServicePlus<TaskMessageConfig, TaskMessageConfigVo, TaskMessageConfigBo> {
}

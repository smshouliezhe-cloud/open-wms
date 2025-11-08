package com.yiruantong.system.controller.task;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.system.domain.task.TaskMessageConfig;
import com.yiruantong.system.domain.task.vo.TaskMessageConfigVo;
import com.yiruantong.system.domain.task.bo.TaskMessageConfigBo;
import com.yiruantong.system.mapper.task.TaskMessageConfigMapper;
import com.yiruantong.system.service.task.ITaskMessageConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息推送配置
 *
 * @author YRT
 * @date 2025-03-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/messageConfig")
public class TaskMessageConfigController extends AbstractController<TaskMessageConfigMapper, TaskMessageConfig, TaskMessageConfigVo, TaskMessageConfigBo> {
}

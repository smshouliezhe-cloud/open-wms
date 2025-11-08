package com.yiruantong.system.controller.task;

import com.yiruantong.system.domain.task.TaskLog;
import com.yiruantong.system.domain.task.bo.TaskLogBo;
import com.yiruantong.system.domain.task.vo.TaskLogVo;
import com.yiruantong.system.mapper.task.TaskLogMapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 任务日志
 *
 * @author YRT
 * @date 2024-12-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/log")
public class TaskLogController extends AbstractController<TaskLogMapper, TaskLog, TaskLogVo, TaskLogBo> {
}

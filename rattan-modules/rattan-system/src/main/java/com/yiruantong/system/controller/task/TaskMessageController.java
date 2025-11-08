package com.yiruantong.system.controller.task;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.system.domain.task.TaskMessage;
import com.yiruantong.system.domain.task.vo.TaskMessageVo;
import com.yiruantong.system.domain.task.bo.TaskMessageBo;
import com.yiruantong.system.mapper.task.TaskMessageMapper;
import com.yiruantong.system.service.task.ITaskMessageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息推送
 *
 * @author YRT
 * @date 2025-03-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/message")
public class TaskMessageController extends AbstractController<TaskMessageMapper, TaskMessage, TaskMessageVo, TaskMessageBo> {
}

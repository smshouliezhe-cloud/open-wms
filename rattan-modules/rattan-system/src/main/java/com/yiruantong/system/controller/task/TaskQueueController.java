package com.yiruantong.system.controller.task;

import com.yiruantong.system.domain.task.TaskQueue;
import com.yiruantong.system.domain.task.bo.TaskQueueBo;
import com.yiruantong.system.domain.task.vo.TaskQueueVo;
import com.yiruantong.system.mapper.task.TaskQueueMapper;
import com.yiruantong.system.service.task.ITaskQueueService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 消息队列
 *
 * @author YRT
 * @date 2024-01-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/queue")
public class TaskQueueController extends AbstractController<TaskQueueMapper, TaskQueue, TaskQueueVo, TaskQueueBo> {

  private final ITaskQueueService taskQueueService;
  /**
   * 重新执行消息队列
   *
   * @param map 前端传入数据
   * @return
   */
  @PostMapping("/push")
  public R<Void> push(@RequestBody Map<String, Object> map) {
    return taskQueueService.push(map);
  }
}

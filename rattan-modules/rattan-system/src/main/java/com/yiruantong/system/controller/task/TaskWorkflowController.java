package com.yiruantong.system.controller.task;

import com.yiruantong.system.domain.task.TaskWorkflow;
import com.yiruantong.system.domain.task.bo.TaskWorkflowBo;
import com.yiruantong.system.domain.task.vo.TaskWorkflowVo;
import com.yiruantong.system.mapper.task.TaskWorkflowMapper;
import com.yiruantong.system.service.task.ITaskWorkflowService;
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
 * 业务工作流关联设置
 *
 * @author YRT
 * @date 2024-07-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/workflow")
public class TaskWorkflowController extends AbstractController<TaskWorkflowMapper, TaskWorkflow, TaskWorkflowVo, TaskWorkflowBo> {

  private final ITaskWorkflowService taskWorkflowService;

  /**
   * 根据业务ID获取流程部署ID
   *
   * @param map 前端传入数据
   * @return
   */
  @PostMapping("/getDeployId")
  public R<Map<String, Object>> getDeployId(@RequestBody Map<String, Object> map) {
    return taskWorkflowService.getDeployId(map);
  }
}

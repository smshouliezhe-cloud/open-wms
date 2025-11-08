package com.yiruantong.system.service.task;

import com.yiruantong.system.domain.task.TaskWorkflow;
import com.yiruantong.system.domain.task.bo.TaskWorkflowBo;
import com.yiruantong.system.domain.task.vo.TaskWorkflowVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.Map;

/**
 * 业务工作流关联设置Service接口
 *
 * @author YRT
 * @date 2024-07-03
 */
public interface ITaskWorkflowService extends IServicePlus<TaskWorkflow, TaskWorkflowVo, TaskWorkflowBo> {
  R<Map<String, Object>> getDeployId(Map<String, Object> map);
}

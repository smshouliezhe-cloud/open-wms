package com.yiruantong.system.service.task.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yiruantong.system.domain.task.TaskWorkflow;
import com.yiruantong.system.domain.task.bo.TaskWorkflowBo;
import com.yiruantong.system.domain.task.vo.TaskWorkflowVo;
import com.yiruantong.system.mapper.task.TaskWorkflowMapper;
import com.yiruantong.system.service.task.ITaskWorkflowService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务工作流关联设置Service业务层处理
 *
 * @author YRT
 * @date 2024-07-03
 */
@RequiredArgsConstructor
@Service
public class TaskWorkflowServiceImpl extends ServiceImplPlus<TaskWorkflowMapper, TaskWorkflow, TaskWorkflowVo, TaskWorkflowBo> implements ITaskWorkflowService {
  @Override
  public R<Map<String, Object>> getDeployId(Map<String, Object> map) {
    Long menuId = Convert.toLong(map.get("menuId"));
    Long billId = Convert.toLong(map.get("billId"));
    LambdaQueryWrapper<TaskWorkflow> workflowLambdaQueryWrapper = new LambdaQueryWrapper<>();
    workflowLambdaQueryWrapper.eq(TaskWorkflow::getMenuId, menuId)
      .eq(TaskWorkflow::getBillId, billId);
    TaskWorkflow taskWorkflow = this.getOne(workflowLambdaQueryWrapper);

    Map<String, Object> mapResult = new HashMap<>();
    if (ObjectUtil.isNotNull(taskWorkflow)) {
      mapResult.put("deployId", taskWorkflow.getDeployId());
    }

    return R.ok(mapResult);
  }
}

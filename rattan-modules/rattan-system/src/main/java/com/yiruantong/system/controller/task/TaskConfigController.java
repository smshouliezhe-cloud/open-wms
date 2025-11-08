package com.yiruantong.system.controller.task;

import com.yiruantong.system.domain.task.TaskConfig;
import com.yiruantong.system.domain.task.bo.TaskConfigBo;
import com.yiruantong.system.domain.task.vo.TaskConfigVo;
import com.yiruantong.system.mapper.task.TaskConfigMapper;
import com.yiruantong.system.service.task.ITaskConfigService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 任务配置
 *
 * @author YRT
 * @date 2024-12-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/config")
public class TaskConfigController extends AbstractController<TaskConfigMapper, TaskConfig, TaskConfigVo, TaskConfigBo> {
  private final ITaskConfigService taskConfigService;

  /**
   * 获取通用数据
   */
  @RepeatSubmit()
  @PostMapping("/getPushPageList")
  public R<TableDataInfo<Map<String, Object>>> getPushPageList(HttpServletRequest request, HttpServletResponse response, @RequestBody PageQuery pageQuery) {
    return R.ok(taskConfigService.getPushPageList(request, response, pageQuery));
  }

}

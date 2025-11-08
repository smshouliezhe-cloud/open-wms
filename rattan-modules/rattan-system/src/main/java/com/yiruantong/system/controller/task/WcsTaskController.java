package com.yiruantong.system.controller.task;

import com.yiruantong.system.domain.task.WcsTask;
import com.yiruantong.system.domain.task.bo.WcsTaskBo;
import com.yiruantong.system.domain.task.vo.WcsTaskVo;
import com.yiruantong.system.mapper.task.WcsTaskMapper;
import com.yiruantong.system.service.task.IWcsTaskService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * WCS接口
 *
 * @author YRT
 * @date 2024-10-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/task/task")
public class WcsTaskController extends AbstractController<WcsTaskMapper, WcsTask, WcsTaskVo, WcsTaskBo> {
  private final IWcsTaskService wcsTaskService;

  /**
   * 新增数据
   */
  @Log(title = "推送数据", businessType = BusinessType.PUSH)
  @RepeatSubmit()
  @PostMapping("/push/{ids}")
  public R<Map<String, Object>> push(@PathVariable List<Long> ids) {
    return wcsTaskService.push(ids);
  }
}

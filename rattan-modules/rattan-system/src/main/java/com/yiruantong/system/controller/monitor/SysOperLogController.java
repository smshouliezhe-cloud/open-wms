package com.yiruantong.system.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yiruantong.system.domain.monitor.SysOperLog;
import com.yiruantong.system.domain.monitor.bo.SysOperLogBo;
import com.yiruantong.system.domain.monitor.vo.SysOperLogVo;
import com.yiruantong.system.mapper.monitor.SysOperLogMapper;
import com.yiruantong.system.service.monitor.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志记录
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/monitor/operLog")
public class SysOperLogController extends AbstractController<SysOperLogMapper, SysOperLog, SysOperLogVo, SysOperLogBo> {

  private final ISysOperLogService operLogService;

  /**
   * 获取操作日志记录列表
   */
  @SaCheckPermission("monitor:operlog:list")
  @GetMapping("/list")
  public TableDataInfo<SysOperLogVo> list(SysOperLogBo operLog, PageQuery pageQuery) {
    return operLogService.selectPageOperLogList(operLog, pageQuery);
  }
}

package com.yiruantong.system.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yiruantong.system.domain.monitor.SysLogininfor;
import com.yiruantong.system.domain.monitor.bo.SysLogininforBo;
import com.yiruantong.system.domain.monitor.vo.SysLogininforVo;
import com.yiruantong.system.mapper.monitor.SysLogininforMapper;
import com.yiruantong.system.service.monitor.ISysLogininforService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.constant.GlobalConstants;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.excel.utils.ExcelUtil;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.redis.utils.RedisUtils;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统访问记录
 *
 * @author YiRuanTong
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/monitor/logininfor")
public class SysLogininforController extends AbstractController<SysLogininforMapper, SysLogininfor, SysLogininforVo, SysLogininforBo> {

  private final ISysLogininforService logininforService;

  /**
   * 获取系统访问记录列表
   */
  @SaCheckPermission("monitor:logininfor:list")
  @GetMapping("/list")
  public TableDataInfo<SysLogininforVo> list(SysLogininforBo logininfor, PageQuery pageQuery) {
    return logininforService.selectPageLogininforList(logininfor, pageQuery);
  }

  /**
   * 导出系统访问记录列表
   */
  @Log(title = "登录日志", businessType = BusinessType.EXPORT)
  @SaCheckPermission("monitor:logininfor:export")
  @PostMapping("/export")
  public void export(SysLogininforBo logininfor, HttpServletResponse response) {
    List<SysLogininforVo> list = logininforService.selectLogininforList(logininfor);
    ExcelUtil.exportExcel(list, "登录日志", SysLogininforVo.class, response);
  }

  @SaCheckPermission("monitor:logininfor:unlock")
  @Log(title = "账户解锁", businessType = BusinessType.OTHER)
  @GetMapping("/unlock/{userName}")
  public R<Void> unlock(@PathVariable("userName") String userName) {
    String loginName = GlobalConstants.PWD_ERR_CNT_KEY + userName;
    if (RedisUtils.hasKey(loginName)) {
      RedisUtils.deleteObject(loginName);
    }
    return R.ok();
  }

}

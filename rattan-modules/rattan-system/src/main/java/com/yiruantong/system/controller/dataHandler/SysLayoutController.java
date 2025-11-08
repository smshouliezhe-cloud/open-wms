package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysLayout;
import com.yiruantong.system.domain.dataHandler.bo.SysLayoutBo;
import com.yiruantong.system.domain.dataHandler.vo.SysLayoutVo;
import com.yiruantong.system.mapper.dataHandler.SysLayoutMapper;
import com.yiruantong.system.service.dataHandler.ISysLayoutService;
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
 * 【请填写功能名称】
 *
 * @author ${author}
 * @date 2024-01-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/layout")
public class SysLayoutController extends AbstractController<SysLayoutMapper, SysLayout, SysLayoutVo, SysLayoutBo> {
  private final ISysLayoutService sysLayoutService;
  //#region InitLayout 初始化首页布局页面
  /**
   * 初始化首页布局页面
   */
  @PostMapping("/initLayout")
  public Map<String, Object> initLayout(@RequestBody Map<String, Object> map) {
    return sysLayoutService.initLayout(map);
  }
  //#endregion

  //#region 保存
  /**
   * 保存
   */
  @PostMapping("/saveLayout")
  public R<Void> saveLayout(@RequestBody Map<String, Object> map) {
    return sysLayoutService.saveLayout(map);
  }
  //#endregion
}

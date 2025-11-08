package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysMenuApp;
import com.yiruantong.system.domain.dataHandler.bo.SysMenuAppBo;
import com.yiruantong.system.domain.dataHandler.vo.SysMenuAppVo;
import com.yiruantong.system.mapper.dataHandler.SysMenuAppMapper;
import com.yiruantong.system.service.dataHandler.ISysMenuAppService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
@RequestMapping("/system/dataHandler/menuApp")
public class SysMenuAppController extends AbstractController<SysMenuAppMapper, SysMenuApp, SysMenuAppVo, SysMenuAppBo> {
  private final ISysMenuAppService sysMenuAppService;

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return sysMenuAppService.searchTree(filterText);
  }
}

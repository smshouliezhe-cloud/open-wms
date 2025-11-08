package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysCodeRegular;
import com.yiruantong.system.domain.dataHandler.bo.SysCodeRegularBo;
import com.yiruantong.system.domain.dataHandler.vo.SysCodeRegularVo;
import com.yiruantong.system.mapper.dataHandler.SysCodeRegularMapper;
import com.yiruantong.system.service.dataHandler.ISysCodeRegularService;
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
 * 单据编码规则
 *
 * @author YRT
 * @date 2023-07-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/codeRegular")
public class SysCodeRegularController extends AbstractController<SysCodeRegularMapper, SysCodeRegular, SysCodeRegularVo, SysCodeRegularBo> {
  private final ISysCodeRegularService sysCodeRegularService;

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return sysCodeRegularService.searchTree(filterText);
  }
}

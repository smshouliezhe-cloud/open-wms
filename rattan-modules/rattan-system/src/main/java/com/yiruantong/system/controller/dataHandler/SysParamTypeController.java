package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysParamType;
import com.yiruantong.system.domain.dataHandler.bo.SysParamTypeBo;
import com.yiruantong.system.domain.dataHandler.vo.SysParamTypeVo;
import com.yiruantong.system.mapper.dataHandler.SysParamTypeMapper;
import com.yiruantong.system.service.dataHandler.ISysParamTypeService;
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
 * 下拉框设置
 *
 * @author YRT
 * @date 2023-07-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/paramType")
public class SysParamTypeController extends AbstractController<SysParamTypeMapper, SysParamType, SysParamTypeVo, SysParamTypeBo> {
  private final ISysParamTypeService sysParamTypeService;

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return sysParamTypeService.searchTree(filterText);
  }
}

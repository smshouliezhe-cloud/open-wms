package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysDropdown;
import com.yiruantong.system.domain.dataHandler.bo.SysDropdownBo;
import com.yiruantong.system.domain.dataHandler.vo.SysDropdownVo;
import com.yiruantong.system.mapper.dataHandler.SysDropdownMapper;
import com.yiruantong.system.service.dataHandler.ISysDropdownService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 下拉框设置
 *
 * @author YRT
 * @date 2023-07-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/dropdown")
public class SysDropdownController extends AbstractController<SysDropdownMapper, SysDropdown, SysDropdownVo, SysDropdownBo> {
  private final ISysDropdownService sysDropdownService;

  /**
   * 下拉框值查询
   */
  @PostMapping("/loadDropDown")
  public R<Map<String, Object>> loadDropDown(@RequestBody Map<String, Object> map) {
    return sysDropdownService.loadDropDown(map);
  }

  /**
   * 下拉框值查询
   */
  @PostMapping("/loadDropDownById/{id}")
  public R<Map<String, Object>> loadDropDownById(@PathVariable Long id) {
    return sysDropdownService.loadDropDownById(id);
  }

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return sysDropdownService.searchTree(filterText);
  }
}

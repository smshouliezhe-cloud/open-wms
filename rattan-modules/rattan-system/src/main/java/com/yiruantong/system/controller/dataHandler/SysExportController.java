package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysExport;
import com.yiruantong.system.domain.dataHandler.bo.SysExportBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportColumnVo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportVo;
import com.yiruantong.system.mapper.dataHandler.SysExportMapper;
import com.yiruantong.system.service.dataHandler.ISysExportService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 导出设置
 *
 * @author YRT
 * @date 2023-08-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/export")
public class SysExportController extends AbstractController<SysExportMapper, SysExport, SysExportVo, SysExportBo> {
  private final ISysExportService sysExportService;

  /**
   * 更新标题
   */
  @PostMapping("/saveFieldLeft")
  public R<Void> saveFieldLeft(@RequestBody Map<String, Object> map) {
    return sysExportService.saveFieldLeft(map);
  }

  /**
   * 更新标题
   */
  @PostMapping("/saveModule")
  public R<Void> saveModule(@RequestBody Map<String, Object> map) {
    return sysExportService.saveModule(map);
  }

  /**
   * 删除字段
   */
  @PostMapping("/deleteField")
  public R<Void> deleteField(@RequestBody Map<String, Object> map) {
    return sysExportService.deleteField(map);
  }

  /**
   * 开启是否可用
   */
  @PostMapping("/changeEnable")
  public R<Void> changeEnable(@RequestBody Map<String, Object> map) {
    return sysExportService.changeEnable(map);
  }

  /**
   * 导入模块
   */
  @PostMapping("/importModule")
  public R<Void> importModule(@RequestBody Map<String, Object> map) {
    return sysExportService.importModule(map);
  }

  /**
   * 删除模块
   */
  @PostMapping("/deleteModule")
  public R<Void> deleteModule(@RequestBody Map<String, Object> map) {
    return sysExportService.deleteModule(map);
  }

  /**
   * 导出模块
   */
  @PostMapping("/exportData")
  public void exportData(HttpServletRequest request, HttpServletResponse response, String pageQuery) {
    try {
      sysExportService.exportData(request, response, pageQuery);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 删除模块
   */
  @PostMapping("/selectExportColumnList")
  public R<List<SysExportColumnVo>> selectExportColumnList(@RequestBody Map<String, Object> map) {
    return sysExportService.selectExportColumnList(map);
  }

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return sysExportService.searchTree(filterText);
  }
}

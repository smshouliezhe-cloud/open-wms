package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysExportVueData;
import com.yiruantong.system.domain.dataHandler.bo.SysExportVueDataBo;
import com.yiruantong.system.domain.dataHandler.vo.SysExportVueDataVo;
import com.yiruantong.system.mapper.dataHandler.SysExportVueDataMapper;
import com.yiruantong.system.service.dataHandler.ISysExportVueDataService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 导出视图设置
 *
 * @author YRT
 * @date 2023-08-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/exportVueData")
public class SysExportVueDataController extends AbstractController<SysExportVueDataMapper, SysExportVueData, SysExportVueDataVo, SysExportVueDataBo> {
  private final ISysExportVueDataService sysExportVueDataService;

  /**
   * 根据exportId获取VueData集合
   *
   * @param map 导出ID
   * @return 返回VueData集合
   */
  @PostMapping("/selectByExportId")
  public R<List<SysExportVueDataVo>> selectByExportId(@RequestBody Map<String, Object> map) {
    return sysExportVueDataService.selectByExportId(map);
  }

  /**
   * 更新标题
   */
  @PostMapping("/updateTitle")
  public R<Void> updateTitle(@RequestBody Map<String, Object> map) {
    return sysExportVueDataService.updateTitle(map);
  }

  /**
   * 更新标题
   */
  @PostMapping("/saveData")
  public R<Map<String, Object>> saveData(@RequestBody Map<String, Object> map) {
    return sysExportVueDataService.saveData(map);
  }
}

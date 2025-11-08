package com.yiruantong.system.controller.dataHandler;

import com.yiruantong.system.domain.dataHandler.SysImport;
import com.yiruantong.system.domain.dataHandler.bo.SysImportBo;
import com.yiruantong.system.domain.dataHandler.vo.SysImportVo;
import com.yiruantong.system.mapper.dataHandler.SysImportMapper;
import com.yiruantong.system.service.dataHandler.ISysImportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 导入设置
 *
 * @author YRT
 * @date 2023-08-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dataHandler/import")
public class SysImportController extends AbstractController<SysImportMapper, SysImport, SysImportVo, SysImportBo> {
  private final ISysImportService sysImportService;

  /**
   * 下载导入模板
   */
  @PostMapping("/importTemplate")
  public void importTemplate(HttpServletResponse response, Long importId) {
    try {
      sysImportService.importTemplate(response, importId);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 搜索菜单
   */
  @PostMapping("/searchTree/{filterText}")
  public R<List<Map<String, Object>>> searchTree(@PathVariable("filterText") String filterText) {
    return sysImportService.searchTree(filterText);
  }
}

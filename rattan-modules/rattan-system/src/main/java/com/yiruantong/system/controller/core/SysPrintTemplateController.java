package com.yiruantong.system.controller.core;

import com.yiruantong.system.domain.core.SysPrintTemplate;
import com.yiruantong.system.domain.core.bo.SysPrintTemplateBo;
import com.yiruantong.system.domain.core.vo.SysPrintTemplateVo;
import com.yiruantong.system.mapper.core.SysPrintTemplateMapper;
import com.yiruantong.system.service.core.ISysPrintTemplateService;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 系统打印模板
 *
 * @author YRT
 * @date 2023-11-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/core/printTemplate")
public class SysPrintTemplateController extends AbstractController<SysPrintTemplateMapper, SysPrintTemplate, SysPrintTemplateVo, SysPrintTemplateBo> {
  private final ISysPrintTemplateService sysPrintTemplateService;

  /**
   * 保存模板
   */
  @PostMapping("/savePrintTemplate")
  public R<Void> savePrintTemplate(@RequestBody SysPrintTemplateBo bo) {
    return sysPrintTemplateService.savePrintTemplate(bo);
  }

  /**
   * 获取打印模板
   */
  @PostMapping("/getTemplateListByMenuId")
  public R<List<SysPrintTemplateVo>> getTemplateListByMenuId(@RequestBody Map<String, Long> map) {
    return sysPrintTemplateService.getTemplateListByMenuId(map);
  }
}

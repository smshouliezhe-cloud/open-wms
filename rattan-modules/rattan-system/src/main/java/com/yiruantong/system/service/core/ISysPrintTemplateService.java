package com.yiruantong.system.service.core;

import com.yiruantong.system.domain.core.SysPrintTemplate;
import com.yiruantong.system.domain.core.bo.SysPrintTemplateBo;
import com.yiruantong.system.domain.core.vo.SysPrintTemplateVo;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;

import java.util.List;
import java.util.Map;

/**
 * 系统打印模板Service接口
 *
 * @author YRT
 * @date 2023-11-14
 */
public interface ISysPrintTemplateService extends IServicePlus<SysPrintTemplate, SysPrintTemplateVo, SysPrintTemplateBo> {
  /**
   * 保存模板
   */
  R<Void> savePrintTemplate(SysPrintTemplateBo bo);

  /**
   * 获取打印模板
   */
  R<List<SysPrintTemplateVo>> getTemplateListByMenuId(Map<String, Long> map);
}

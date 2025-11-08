package com.yiruantong.common.translation.core.impl;

import com.yiruantong.common.core.service.OssService;
import com.yiruantong.common.translation.annotation.TranslationType;
import com.yiruantong.common.translation.constant.TransConstant;
import com.yiruantong.common.translation.core.TranslationInterface;
import lombok.AllArgsConstructor;

/**
 * OSS翻译实现
 *
 * @author YiRuanTong
 */
@AllArgsConstructor
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
public class OssUrlTranslationImpl implements TranslationInterface<String> {

  private final OssService ossService;

  @Override
  public String translation(Object key, String other) {
    if (key instanceof String ids) {
      return ossService.selectUrlByIds(ids);
    } else if (key instanceof Long id) {
      return ossService.selectUrlByIds(id.toString());
    }
    return null;
  }
}

package com.yiruantong.composite.service.inventory;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface IErpStorageOuterService {
  /**
   * 确认出库
   * @param map
   * @return
   */
  R<Void> toOutOrder(Map<String, Object> map);
}

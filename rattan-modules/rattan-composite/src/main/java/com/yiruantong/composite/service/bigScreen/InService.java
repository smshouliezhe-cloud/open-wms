package com.yiruantong.composite.service.bigScreen;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface InService {
  /**
   * 查询入库统计
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> inOrderStat(Map<String, Object> map);
}

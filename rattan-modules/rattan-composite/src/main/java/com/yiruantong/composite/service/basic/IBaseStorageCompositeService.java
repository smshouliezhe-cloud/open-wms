package com.yiruantong.composite.service.basic;

import com.yiruantong.common.core.domain.R;

import java.util.List;
import java.util.Map;

public interface IBaseStorageCompositeService {
  /**
   * 删除前事件
   * @param Ids
   * @return
   */
  int deleteByIds(Long[] Ids);

  R<List<Map<String, Object>>> getGridData();
}

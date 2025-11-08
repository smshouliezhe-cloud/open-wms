package com.yiruantong.composite.service.inventory;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface IInventoryService {
  int deleteByIds(Long[] ids);

  R<Map<String,Object>> getBadgeCount();
}

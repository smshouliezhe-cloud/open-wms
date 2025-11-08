package com.yiruantong.inventory.service.core;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inventory.domain.core.bo.ApiCoreInventoryBo;

import java.util.List;
import java.util.Map;

public interface IApiCoreInventoryService {

  /**
   * API商品库存查询
   *
   * @param bo 查询条件
   * @return 返回查询列表数据
   */
  R<List<Map<String, Object>>> selectInventoryList(ApiCoreInventoryBo bo);
}

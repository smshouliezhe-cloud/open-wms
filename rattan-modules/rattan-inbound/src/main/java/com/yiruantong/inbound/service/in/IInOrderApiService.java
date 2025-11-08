package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;

import java.util.Map;

public interface IInOrderApiService {
  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiInOrderBo bo);
  /**
   * 修改数据
   */
  R<Map<String, Object>> updateInOrder(ApiInOrderBo bo);
}

package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.api.ApiOutOrderPlanBo;

import java.util.Map;

public interface IOutOrderPlanApiService {
  /**
   * 接口add
   *
   * @param bo
   * @return
   */
  R<Map<String, Object>> add(ApiOutOrderPlanBo bo);
}

package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.api.ApiInOrderPlanBo;

import java.util.Map;

public interface IInOrderPlanApiService {

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiInOrderPlanBo bo);
}

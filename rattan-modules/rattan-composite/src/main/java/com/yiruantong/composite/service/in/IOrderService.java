package com.yiruantong.composite.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;

import java.util.Map;

public interface IOrderService {
  /**
   * 根据ERP扣重退货自动扣减库存
   */
  R<Map<String, Object>> inOrderChecking(ApiInOrderBo bo);
}

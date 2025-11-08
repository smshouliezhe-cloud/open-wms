package com.yiruantong.composite.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.api.ApiOutOrderBo;
import com.yiruantong.outbound.domain.out.OutOrder;

import java.util.Map;

public interface IAutoReturnOrderService {

  /**
   * 出库单退货-自动回退库存
   */
  R<Map<String, Object>> toReturnOrder(ApiOutOrderBo bo);

  Long toOutReturn(OutOrder outOrder);

  Long createInOrder(Long returnId);

  R<Void> saveCheck(Long newOrderId, Long returnId);

}

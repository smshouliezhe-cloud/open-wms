package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.api.ApiOutOrderBo;

import java.util.Map;

public interface IOutOrderApiService {

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiOutOrderBo bo);

  /**
   * 取消出库单
   */
  R<Map<String, Object>> cancel(ApiOutOrderBo bo);

  /**
   * 更新出库单订单状态
   */
  R<Map<String, Object>> updateOrderStatus(ApiOutOrderBo bo);


}

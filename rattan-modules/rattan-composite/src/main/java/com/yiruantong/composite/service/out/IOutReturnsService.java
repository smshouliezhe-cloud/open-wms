package com.yiruantong.composite.service.out;

import com.yiruantong.common.core.domain.R;

import java.util.List;
import java.util.Map;

public interface IOutReturnsService {

  /**
   * 出库退货单转预到货
   *
   * @param map 前端参数
   */
  R<Void> toInOrder(Map<String, Object> map);

  /**
   * 确认入库
   *
   * @param ids 前端参数
   */
  R<Void> saveCheck(List<Long> ids);

  /**
   * 创建预到货
   *
   * @param orderId 单据ID
   * @return
   */
  Long createOrderCode(Long orderId);
}

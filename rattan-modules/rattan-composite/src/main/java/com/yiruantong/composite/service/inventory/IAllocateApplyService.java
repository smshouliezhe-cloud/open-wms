package com.yiruantong.composite.service.inventory;

import cn.hutool.core.lang.Dict;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.rabbitmq.domain.RabbitReceiverDto;

import java.util.Map;

public interface IAllocateApplyService {
  /**
   * 转到出库单
   *
   * @param map 转到出库单
   * @return
   */
  R<Void> toOutOrder(Map<String, Object> map);
}

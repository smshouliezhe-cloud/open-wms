package com.yiruantong.outbound.liteflow.Context;

import lombok.Data;
import com.yiruantong.outbound.domain.out.OutOrder;

import java.util.List;

/**
 * 出库单审核上下文
 */
@Data
public class OutOrderAuditingContext {
  /**
   * 扫描入库bo数据
   */
  List<OutOrder> outOrders;
}

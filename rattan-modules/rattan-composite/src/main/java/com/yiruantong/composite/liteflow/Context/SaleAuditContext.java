package com.yiruantong.composite.liteflow.Context;

import lombok.Data;

import java.util.List;


/**
 * 销售单审核上下文
 */
@Data
public class SaleAuditContext {
  /**
   * 销售订单id
   */
  List<Long> ids;
}

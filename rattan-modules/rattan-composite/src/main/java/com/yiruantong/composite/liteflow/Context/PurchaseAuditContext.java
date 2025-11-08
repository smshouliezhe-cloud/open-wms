package com.yiruantong.composite.liteflow.Context;

import lombok.Data;
import com.yiruantong.basic.domain.client.BaseClient;

import java.util.List;


/**
 * 采购单审核上下文
 */
@Data
public class PurchaseAuditContext {
  /**
   * 采购单id
   */
  List<Long> ids;

  /**
   * 客户信息
   */
  BaseClient baseClient;
}

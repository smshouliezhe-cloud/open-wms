package com.yiruantong.composite.liteflow.Context;

import lombok.Data;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.inbound.domain.in.InOrder;

import java.util.List;


/**
 * 采购单一键入库上下文
 */
@Data
public class PurchaseToInOrderContext {
  /**
   * 采购单id
   */
  List<Long> ids;

  /**
   * 预到货单信息
   */
  InOrder inOrder;

  InventorySourceTypeEnum inventorySourceTypeEnum;
}

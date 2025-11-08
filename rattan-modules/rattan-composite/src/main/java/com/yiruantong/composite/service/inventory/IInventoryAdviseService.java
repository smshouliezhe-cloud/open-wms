package com.yiruantong.composite.service.inventory;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;

import java.util.List;

public interface IInventoryAdviseService {

  R<Void> toPurchaseOrder(List<CoreInventoryAdvise> dataList);

  R<Void> toTmsQuotation(List<CoreInventoryAdvise> dataList);
}

package com.yiruantong.composite.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.out.bo.OrderDetailLackBo;

import java.util.List;

public interface IOutOrderLockService {
  /**
   * 生成预到货单
   *
   * @param dataList 数据集合
   * @return 提示信息
   */
  R<Void> toPurchaseOrder(List<OrderDetailLackBo> dataList);

  /**
   * 生成入库计划单
   *
   * @param dataList 数据集合
   * @return 提示信息
   */
  R<Void> toTmsQuotation(List<OrderDetailLackBo> dataList);

  R<Void> orderCancel(List<Long> ids);

  R<Void> cancelApply(List<Long> ids);

  R<Void> pdaSorting(List<Long> ids);
}

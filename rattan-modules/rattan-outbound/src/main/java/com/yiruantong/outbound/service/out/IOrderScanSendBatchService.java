package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;

import java.util.Map;

public interface IOrderScanSendBatchService {
  /**
   * 闪电发货效验 - 获取波次下的订单数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getSendBatchData(Map<String, Object> map);

  R<Map<String, Object>> saveSendBatchData(OutScanMainBo outScanMainBo);

  /**
   * 发货校验 - 获取订单数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getCheckExpressCode(Map<String, Object> map);

  /**
   * 发货校验 - 效验订单
   *
   * @param map 前端参数
   */
  R<Void> orderSave(Map<String, Object> map);

  /**
   * 修改出库单
   */
  R<Void> updateOutOrder(OutOrder outOrder);

  /**
   * 修改波次明细
   */
  R<Void> updateOutOrderWaveDetail(OutOrderWave outOrderWave,String OrderCode);
}

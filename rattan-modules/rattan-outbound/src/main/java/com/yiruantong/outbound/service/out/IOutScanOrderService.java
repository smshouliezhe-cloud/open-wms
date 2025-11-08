package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutOrderScanBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;

import java.util.Map;

public interface IOutScanOrderService {
  /**
   * 获取出库单扫描数据
   *
   * @param outOrderScanBo 扫描参数
   */
  R<Map<String, Object>> getOutOrderData(OutOrderScanBo outOrderScanBo);

  /**
   * 一键闪入-获取出库单扫描数据
   *
   * @param outOrderScanBo 扫描参数
   */
  R<Map<String, Object>> getOutOrderDataFlashIn(OutOrderScanBo outOrderScanBo);

  /**
   * 保存出库单扫描数据
   *
   * @param outScanMainBo 前端参数
   */
  R<OutOrder> normalOutSave(OutScanMainBo outScanMainBo);

  /**
   * 获取波次打包扫描数据
   *
   * @param map
   * @return
   */
  R<Map<String, Object>> getBatchPackageData(Map<String, Object> map);

  /**
   * 保存批量出库单扫描数据
   *
   * @param outScanMainBo 前端参数
   */
  R<Void> saveBatchPackageData(OutScanMainBo outScanMainBo);

  /**
   * 扫拍出库数据
   *
   * @param outOrderScanBo 前端参数
   */
  R<Map<String, Object>> getOutOrderPlate(OutOrderScanBo outOrderScanBo);
}

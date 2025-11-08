package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;

import java.util.List;
import java.util.Map;

/**
 * 出库拣货下架
 *
 * @author xtb
 * Created 2023-12-09
 */
public interface IOutScanPickingService {
  /**
   * 获取拣货下架数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getPickingData(Map<String, Object> map);

  /**
   * 获取摘果下架数据
   *
   * @param map 前端参数
   */
  R<Map<String, Object>> getZgPickingData(Map<String, Object> map);

  /**
   * 拣货人员领取任务
   *
   * @param map 前端参数
   */
  R<Void> receiveTask(Map<String, Object> map);

  /**
   * 保存扫描拣货下架
   *
   * @param outScanMainBo 扫描参数
   * @return 扫描下架结果
   */
  R<Map<String, Object>> savePickingScan(OutScanMainBo outScanMainBo);

  /**
   * 获取拣货下架回拣数据
   *
   * @param map 前端参数
   * @return 扫描下架结果
   */
  R<List<CoreInventoryComposeVo>> getOffPositionShelveData(Map<String, Object> map);

  /**
   * 保存拣货下架回拣数据
   *
   * @param storageScanPositionTransferBo 保存参数
   * @return R
   */
  R<Void> saveOffPositionShelveData(ScanPositionTransferBo storageScanPositionTransferBo);

  /**
   * SN校验参数
   *
   * @param map 校验参数
   * @return R
   */
  R<Void> checkSn(Map<String, Object> map);

}

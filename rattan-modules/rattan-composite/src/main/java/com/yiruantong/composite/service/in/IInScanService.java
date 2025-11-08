package com.yiruantong.composite.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;

import java.util.List;
import java.util.Map;

public interface IInScanService {
  /**
   * 码盘扫描入库 - 获取码盘数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<List<Map<String, Object>>> getEnterStackingData(Map<String, Object> map);

  /**
   * 码盘扫描入库 - 保存
   *
   * @param storageScanPositionTransferBo 实体对象
   * @return 返回查询数据
   */

  R<Void> saveEnterStacking(ScanPositionTransferBo storageScanPositionTransferBo);
}

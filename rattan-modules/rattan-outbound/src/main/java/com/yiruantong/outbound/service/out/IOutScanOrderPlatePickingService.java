package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

public interface IOutScanOrderPlatePickingService {
  /**
   * 按拍下架 - 获取扫描数据
   * @param map 前端参数
   */
  R<Map<String, Object>> getOutOrderWaveList(Map<String, Object> map);
}

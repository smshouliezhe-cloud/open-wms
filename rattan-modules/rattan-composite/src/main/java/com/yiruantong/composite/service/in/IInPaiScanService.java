package com.yiruantong.composite.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

import java.util.Map;

public interface IInPaiScanService {
  /**
   * 按单码盘扫描保存前校验拍号是否存在
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Void> saveCheckPlateCode(Map<String, Object> map);

  /**
   * 常规扫描入库 - 按单码盘扫描
   *
   * @param inScanOrderBo 常规扫描入库数据
   * @return 返回查询数据
   */
  R<Void> scanPlateInSave(InScanOrderBo inScanOrderBo);
}

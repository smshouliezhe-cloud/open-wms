package com.yiruantong.inbound.service.in;

import com.yiruantong.basic.domain.storage.BasePlate;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

import java.util.List;
import java.util.Map;

/**
 * LPN扫描
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
public interface IInScanLpnService {

  R<Void> scanPlateInSave(InScanOrderBo inScanOrderBo);

  /**
   * - LPN号扫描上架 -- 获取数据
   *
   * @param map 前段传入数据
   * @return 返回查询数据
   */
  List<Map<String, Object>> getShelveInfoByLpnCode(Map<String, Object> map);
}

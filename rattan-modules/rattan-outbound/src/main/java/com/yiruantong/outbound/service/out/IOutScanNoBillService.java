package com.yiruantong.outbound.service.out;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;

import java.util.List;
import java.util.Map;

public interface IOutScanNoBillService {
  /**
   * 获取无单扫描商品信息
   * @param map
   * @return
   */
  R<List<CoreInventoryComposeVo>> getNoBillProduct(Map<String, Object> map);

  /**
   * 无单扫描保存
   *
   * @param outScanMainBo
   * @return
   */
  R<OutOrder> noBillOutSave(OutScanMainBo outScanMainBo);
}

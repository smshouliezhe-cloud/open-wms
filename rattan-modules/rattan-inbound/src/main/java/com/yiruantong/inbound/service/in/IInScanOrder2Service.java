package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.common.core.enums.in.QualityCheckTypeEnum;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

import java.util.List;
import java.util.Map;

/**
 * 预到货单Service接口
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
public interface IInScanOrder2Service {
  /**
   * 保存货代扫描入库
   *
   * @param inScanOrderBo 前端参数
   */
  R<Void> saveOrderFreight(InScanOrderBo inScanOrderBo);


}

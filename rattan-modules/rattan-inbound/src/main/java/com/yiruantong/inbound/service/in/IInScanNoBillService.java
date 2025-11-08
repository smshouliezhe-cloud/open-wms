package com.yiruantong.inbound.service.in;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;

public interface IInScanNoBillService {
  /**
   * 无单扫描确认入库
   * @param inScanOrderBoap
   * @return
   */
  R<Void> noBillEnterSave(InScanOrderBo inScanOrderBoap);
}

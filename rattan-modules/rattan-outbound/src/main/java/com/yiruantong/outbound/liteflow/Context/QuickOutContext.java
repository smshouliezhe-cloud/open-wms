package com.yiruantong.outbound.liteflow.Context;

import lombok.Data;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;


@Data
public class QuickOutContext {
  /**
   * 扫描出库bo数据
   */
  OutScanMainBo outScanMainBo;

  /**
   * 出库单信息
   */
  OutOrder outOrder;

}

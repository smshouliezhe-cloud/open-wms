package com.yiruantong.outbound.domain.out.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.base.QrCodeTypeEnum;

@Data
@NoArgsConstructor
public class OutOrderScanBo {
  /**
   * 出库单ID
   */
  private Long orderId;

  /**
   * 出库编号
   */
  private String orderCode;

  /**
   * 二维码类型
   */
  private QrCodeTypeEnum qrCodeType;
}

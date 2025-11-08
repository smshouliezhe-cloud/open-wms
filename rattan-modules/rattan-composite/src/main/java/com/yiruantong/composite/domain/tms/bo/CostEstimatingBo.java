package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 费用估算
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CostEstimatingBo {

  /**
   * 运费明细数据
   */
  LogisticsInfoBo logisticsInfo;
  /**
   * 运单明细ID
   */
  private List<Long> wayBillDetailIds;
  private String carrierName;
  private BigDecimal totalWeight;
  private BigDecimal totalVolume;
  private String storageName;

}

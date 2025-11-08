package com.yiruantong.composite.domain.tms.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 运输单运费载配（组合下发）
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TransportTaskBo {

  /**
   * 表单上面的数据
   */
  FormInfoBo formInfo;
  /**
   * 表单明细表一，物流分段明细
   */
  List<LogisticsCostBo> tableData;
  /**
   * 表单的明细表二，商品信息明细
   */
  List<ProductListBo> productList;
  /**
   * 运单主表ID
   */
  private List<Long> wayBillIds;
  /**
   * 运单明细ID
   */
  private List<Long> wayBillDetailIds;
  private BigDecimal totalSegmentFreight;

  // ------------分割线--------------------
  // ------------下面为运输单内容--------------------
  /**
   * 运输单主表ID
   */
  private List<Long> transportIds;

}

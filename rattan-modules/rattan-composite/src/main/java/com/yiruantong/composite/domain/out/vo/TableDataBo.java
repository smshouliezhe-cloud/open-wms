package com.yiruantong.composite.domain.out.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TableDataBo {
  /**
   * 科目Id
   */
  private Long feeItemId;
  /**
   * 科目编号
   */
  private String feeItemCode;
  /**
   * 科目名称
   */
  private String feeItemName;

  /**
   * 计费方式
   */
  private String pricingManner;

  /**
   * 价格
   */
  private BigDecimal price;


}

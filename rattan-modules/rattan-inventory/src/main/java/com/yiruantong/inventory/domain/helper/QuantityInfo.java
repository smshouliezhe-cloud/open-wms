package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按到货单商品数量 QuantityInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class QuantityInfo {

  /**
   * 最小值
   */
  private String quantityMin;

  /**
   * 最大值
   */
  private String quantityMax;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}

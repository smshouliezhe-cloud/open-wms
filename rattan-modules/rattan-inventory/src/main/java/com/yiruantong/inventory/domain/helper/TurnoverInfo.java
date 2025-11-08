package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按商品周转率 TurnoverInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class TurnoverInfo {

  /**
   * 最小值
   */
  private String turnoverMin;

  /**
   * 最大值
   */
  private String turnoverMax;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}

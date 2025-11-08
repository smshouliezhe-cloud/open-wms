package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 批次号 BatchNumberInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class BatchNumberInfo {

  /**
   * 批次号
   */
  private Boolean batchNumber;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}

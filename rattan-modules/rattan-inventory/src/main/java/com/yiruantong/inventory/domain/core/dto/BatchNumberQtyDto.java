package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 货位数量规则
 */
@Data
@NoArgsConstructor
public class BatchNumberQtyDto {
  /**
   * 数量
   */
  BigDecimal quantity;
  /**
   * 批次号
   */
  String batchNumber;
  /**
   * 货位
   */
  String positionName;
}

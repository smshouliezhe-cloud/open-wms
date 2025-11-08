package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 货位数量规则
 */
@Data
@NoArgsConstructor
public class PositionNameQtyDto {
  /**
   * 数量
   */
  BigDecimal quantity;
  /**
   * 货位
   */
  String positionName;
}

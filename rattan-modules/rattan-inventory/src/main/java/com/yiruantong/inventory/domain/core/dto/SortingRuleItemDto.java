package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.inventory.SortingRuleOperatorEnum;

@Data
@NoArgsConstructor
public class SortingRuleItemDto {
  /**
   * 规则项ID
   */
  String id;
  /**
   * 运算符
   */
  SortingRuleOperatorEnum op;
  /**
   * 规则项ID
   */
  SortingRuleLeftDto left;
  /**
   * 规则项ID
   */
  Object right;
}

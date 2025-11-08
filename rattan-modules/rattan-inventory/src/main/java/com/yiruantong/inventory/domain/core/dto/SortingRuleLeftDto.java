package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.common.core.enums.inventory.SortingRuleTypeEnum;

@Data
@NoArgsConstructor
public class SortingRuleLeftDto {
  /**
   * 类型
   */
  String type;
//  /**
//   * 分拣规则
//   */
//  SortingRuleTypeEnum field;
  /**
   * 分拣规则值
   */
  String field;
}

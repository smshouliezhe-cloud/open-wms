package com.yiruantong.inventory.domain.core.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SortingRuleDto {
  /**
   * 规则ID
   */
  String id;
  /**
   * 规则集
   */
  List<SortingRuleItemDto> children;
  /**
   * 连接词
   */
  String conjunction;
}

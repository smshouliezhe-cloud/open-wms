package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 推荐货位参数 RecommendRegularDto
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class RecommendRegularDto {
  /**
   * 规则名称
   */
  private String name;

  /**
   * 条件
   */
  private Condition condition;

  /**
   * 目标
   */
  private Target target;

  /**
   * 货位优先级
   */
  private List<String> positionOrderBy;
}

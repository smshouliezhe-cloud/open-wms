package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按货位类型推荐 PositionTypeInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class PositionTypeInfo {

  /**
   * 货位类型
   */
  private List<Long> positionTypes;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}

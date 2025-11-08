package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按货位推荐 PositionInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class PositionInfo {

  /**
   * 货位名称
   */
  private String positionNames;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}

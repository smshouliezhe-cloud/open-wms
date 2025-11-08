package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按仓库温层推荐 ThermoclineInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class ThermoclineInfo {

  /**
   * 仓库温层
   */
  private List<String> thermocline;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}

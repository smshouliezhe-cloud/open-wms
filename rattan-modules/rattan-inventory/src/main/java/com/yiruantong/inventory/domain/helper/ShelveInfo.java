package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按货架推荐 ShelveInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class ShelveInfo {

  /**
   * 货架
   */
  private List<String> shelveCodes;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}


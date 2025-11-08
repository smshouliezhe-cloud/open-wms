package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按货主 ConsignorInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class ConsignorInfo {

  /**
   * 货主ID
   */
  private List<Long> consignorList;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}

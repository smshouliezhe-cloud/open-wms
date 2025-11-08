package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按供应商 ProviderInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class ProviderInfo {

  /**
   * 供应商名称
   */
  private String providerShortName;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}

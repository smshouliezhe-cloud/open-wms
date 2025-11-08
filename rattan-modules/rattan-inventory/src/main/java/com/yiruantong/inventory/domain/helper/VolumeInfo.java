package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按商品单位体积 VolumeInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class VolumeInfo {

  /**
   * 最小值
   */
  private String volumeMin;

  /**
   * 最大值
   */
  private String volumeMax;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}

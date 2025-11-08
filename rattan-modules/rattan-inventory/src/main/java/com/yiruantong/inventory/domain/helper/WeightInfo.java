package com.yiruantong.inventory.domain.helper;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 按商品单位重量 WeightInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class WeightInfo {

  /**
   * 最小值
   */
  private String weightMin;

  /**
   * 最大值
   */
  private String weightMax;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}

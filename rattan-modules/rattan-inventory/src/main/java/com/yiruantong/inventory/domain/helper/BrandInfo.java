package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 按商品品牌 ProductInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class BrandInfo {

  /**
   * 品牌Id
   */
  private List<Long> brandList;

  /**
   * 是否选中
   */
  private Boolean isSelect;

}

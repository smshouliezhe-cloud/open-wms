package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.yiruantong.inventory.domain.helper.model.Product;

import java.util.List;

/**
 * 按商品 ProductInfo
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class ProductInfo {

  /**
   * 商品信息
   */
  private List<Product> products;

  /**
   * 是否选中
   */
  private Boolean isSelect;
}



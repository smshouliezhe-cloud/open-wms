package com.yiruantong.inventory.domain.helper.model;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列信息 Product
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class Product {

  /**
   * 商品ID
   */
  private Long productId;

  /**
   * 商品编号
   */
  private String productCode;

  /**
   * 商品名称
   */
  private String productName;

  /**
   * 商品条码
   */
  private String productModel;
}

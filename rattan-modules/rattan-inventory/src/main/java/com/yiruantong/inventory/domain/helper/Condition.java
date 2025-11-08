package com.yiruantong.inventory.domain.helper;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 条件 Condition
 *
 * @author zhenghang
 * @date 2024-01-17
 */
@Data
@NoArgsConstructor
public class Condition {

  /**
   * 按商品品牌
   */
  private BrandInfo brandInfo;

  /**
   * 按商品
   */
  private ProductInfo productInfo;

  /**
   * 按商品类别
   */
  private TypeInfo typeInfo;

  /**
   * 按商品周转率
   */
  private TurnoverInfo turnoverInfo;

  /**
   * 按商品单位体积
   */
  private VolumeInfo volumeInfo;

  /**
   * 按商品单位重量
   */
  private WeightInfo weightInfo;

  /**
   * 按商品单位重量
   */
  private QuantityInfo quantityInfo;

  /**
   * 按货主
   */
  private ConsignorInfo consignorInfo;

  /**
   * 按供应商
   */
  private ProviderInfo providerInfo;

  /**
   * 批次号
   */
  private BatchNumberInfo batchNumberInfo;

  /**
   * 无条件
   */
  private NoCondition noCondition;
}

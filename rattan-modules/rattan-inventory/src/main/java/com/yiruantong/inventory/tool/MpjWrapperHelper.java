package com.yiruantong.inventory.tool;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.system.service.core.ISysConfigService;
import org.springframework.stereotype.Service;

/**
 * mpg查询条件构建
 * @author xietb
 * @date 2023-10-06
 */
@RequiredArgsConstructor
@Service
public class MpjWrapperHelper {
  private final ISysConfigService sysConfigService;

  /**
   * 支持一品多码查询条件
   * @param productModel 查询条码
   */
  public <T> void setSkuToMultiBarcode(String productModel, MPJLambdaWrapper<T> mpjLambdaWrapper) {
// 条码条件
    boolean skuProductToMultiBarcode = sysConfigService.getConfigBool("sku_productToMultiBarcode"); //支持一品多码
    if (skuProductToMultiBarcode) {
      mpjLambdaWrapper.and(
        ObjectUtil.isNotEmpty(productModel),
        a->a.eq(BaseProduct::getProductModel, productModel)
          .or()
          .eq(BaseProduct::getRelationCode, productModel)
          .or()
          .eq(BaseProduct::getRelationCode2, productModel)
          .or()
          .eq(BaseProduct::getRelationCode3, productModel)
          .or()
          .eq(BaseProduct::getRelationCode4,productModel)
          .or()
          .eq(BaseProduct::getRelationCode5, productModel)
          .or()
          .eq(BaseProduct::getMiddleBarcode, productModel)
          .or()
          .eq(BaseProduct::getBigBarcode, productModel)
      );
    } else {
      mpjLambdaWrapper.eq(ObjectUtil.isNotEmpty(productModel), BaseProduct::getProductModel, productModel);
    }
  }

  /**
   * 支持一品多码查询条件
   * @param productModel 查询条码
   */
  public <T> void setSkuToMultiBarcode(String productModel, LambdaQueryWrapper<BaseProduct> wrapper) {
// 条码条件
    boolean skuProductToMultiBarcode = sysConfigService.getConfigBool("sku_productToMultiBarcode"); //支持一品多码
    if (skuProductToMultiBarcode) {
      wrapper.and(
        ObjectUtil.isNotEmpty(productModel),
        a->a.eq(BaseProduct::getProductModel, productModel)
          .or()
          .eq(BaseProduct::getRelationCode, productModel)
          .or()
          .eq(BaseProduct::getRelationCode2, productModel)
          .or()
          .eq(BaseProduct::getRelationCode3, productModel)
          .or()
          .eq(BaseProduct::getRelationCode4,productModel)
          .or()
          .eq(BaseProduct::getRelationCode5, productModel)
          .or()
          .eq(BaseProduct::getMiddleBarcode, productModel)
          .or()
          .eq(BaseProduct::getBigBarcode, productModel)
      );
    } else {
      wrapper.eq(ObjectUtil.isNotEmpty(productModel), BaseProduct::getProductModel, productModel);
    }
  }
}

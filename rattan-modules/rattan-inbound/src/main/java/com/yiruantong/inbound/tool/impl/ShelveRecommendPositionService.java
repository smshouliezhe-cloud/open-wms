package com.yiruantong.inbound.tool.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.product.IBaseProductService;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.inbound.domain.in.InShelve;
import com.yiruantong.inbound.domain.in.InShelveDetail;
import com.yiruantong.inbound.service.in.IInShelveService;
import com.yiruantong.inbound.tool.IShelveRecommendPositionService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShelveRecommendPositionService implements IShelveRecommendPositionService {
  public final IInShelveService inShelveService;
  public final IBaseProductService baseProductService;
  public final IBasePositionService basePositionService;

  //#region 获取推荐货位
  @Override
  public String getRecommendPosition(Long storageId, Long productId) {
    //查询遇到货单货位
    MPJLambdaWrapper<InShelve> wrapper = new MPJLambdaWrapper<InShelve>()
      .select(InShelve::getPositionName)
      .innerJoin(InShelveDetail.class, InShelveDetail::getShelveId, InShelve::getShelveId)
      .eq(InShelveDetail::getProductId, productId)
      .eq(InShelve::getStorageId, storageId)
      .last("limit 1");
    InShelve inShelve = inShelveService.getOne(wrapper);

      if (ObjectUtil.isNotEmpty(inShelve) && ObjectUtil.isNotEmpty(inShelve.getPositionName())) {
        return inShelve.getPositionName();
      }

    //根据商品ID查询货位
    BaseProduct product = baseProductService.getById(productId);
    if (ObjectUtil.isNotEmpty(product) && ObjectUtil.isNotEmpty(product.getPositionName())) {
      return product.getPositionName();
    }

    MPJLambdaWrapper<BasePosition> inventoryMPJLambdaWrapper = new MPJLambdaWrapper<BasePosition>()
      .select(BasePosition::getPositionName)
      .notExists("SELECT  position_name FROM core_inventory as ci  WHERE   ci.position_name = t.position_name AND ci.storage_id =" + storageId)
      .notExists("SELECT  position_id FROM base_position as bp  WHERE   bp.parent_id = t.position_id AND bp.storage_id =" + storageId)
      .eq(BasePosition::getPositionType, 1L)
      .eq(BasePosition::getStorageId, storageId)
      .last("limit 1");
    BasePosition basePosition = basePositionService.getOne(inventoryMPJLambdaWrapper);

      if (ObjectUtil.isNotEmpty(basePosition) && ObjectUtil.isNotEmpty(basePosition.getPositionName())) {
        return basePosition.getPositionName();
      }

    return null;
  }
  //#endregion
}

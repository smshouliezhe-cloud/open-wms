package com.yiruantong.inventory.service.core.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.product.BaseProduct;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.domain.core.bo.ApiCoreInventoryBo;
import com.yiruantong.inventory.service.core.IApiCoreInventoryService;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ApiCoreInventoryServiceImpl implements IApiCoreInventoryService {
  private final IBaseStorageService baseStorageService;
  private final ICoreInventoryService coreInventoryService;

  @Override
  public R<List<Map<String, Object>>> selectInventoryList(ApiCoreInventoryBo bo) {
    try {

      String consignorCode = bo.getConsignorCode();
      String storageCode = bo.getStorageCode();
//      String productCodes = bo.getProductCodes();

      String[] productCodes = StringUtils.split(bo.getProductCodes().toString(), ",");

      Long positionType = bo.getPositionType();
      if (ObjectUtil.isNull(positionType)) {
        positionType = Convert.toLong("1,12,13");
      }

      LambdaQueryWrapper<BaseStorage> storageLambdaWrapper = new LambdaQueryWrapper<>();
      storageLambdaWrapper.eq(BaseStorage::getStorageCode, storageCode);
      BaseStorage storageInfo = baseStorageService.getOnly(storageLambdaWrapper);
      if (ObjectUtil.isNull(storageInfo)) {
        R.fail("没有仓库信息");
      }
      MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<CoreInventory>()
        .select(CoreInventory::getProductStorage, CoreInventory::getStorageStatus, CoreInventory::getHolderStorage,
          CoreInventory::getStorageId, CoreInventory::getStorageName)
        // 货位信息字段
        .select(BasePosition::getPositionType)
        // 商品信息字段
        .select(BaseProduct::getProductId, BaseProduct::getProductCode, BaseProduct::getProductName)
        .innerJoin(BasePosition.class, on -> {
          on.eq(CoreInventory::getStorageId, BasePosition::getStorageId)
            .eq(CoreInventory::getPositionName, BasePosition::getPositionName);
          return on;
        })
        .innerJoin(BaseProduct.class, on -> {
          on.eq(CoreInventory::getProductId, BaseProduct::getProductId);
          return on;
        })
        .selectSum(CoreInventory::getProductStorage)
        .selectSum(CoreInventory::getHolderStorage)

        .gt(CoreInventory::getProductStorage, 0) // 库存量 > 0
        .eq(CoreInventory::getStorageName, storageInfo.getStorageName())
        .in(BasePosition::getPositionType, positionType)
        .in(BaseProduct::getProductCode, bo.getProductCodes())
        .eq(CoreInventory::getConsignorCode, consignorCode)

        .groupBy(CoreInventory::getProductStorage, CoreInventory::getStorageStatus, CoreInventory::getHolderStorage, CoreInventory::getStorageId, CoreInventory::getStorageName)
        .groupBy(BasePosition::getPositionType)
        .groupBy(BaseProduct::getProductId, BaseProduct::getProductCode, BaseProduct::getProductName);


      List<Map<String, Object>> detailList = coreInventoryService.selectJoinMaps(wrapper);
      Assert.isFalse(detailList.isEmpty(), "没有可用的商品库存！");

      // 返回数据
//      Map<String, Object> resultMap = new HashMap<>();
//      resultMap.put("detailList", detailList);
      return R.ok("查询成功", detailList);

    } catch (Exception error) {
      return R.fail("查询失败，" + error.getMessage());
    }
  }
}

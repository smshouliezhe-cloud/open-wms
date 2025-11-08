package com.yiruantong.inventory.service.replenishment.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.domain.replenishment.bo.StorageReplenishmentDetailBo;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentDetailVo;
import com.yiruantong.inventory.mapper.replenishment.StorageReplenishmentDetailMapper;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentDetailService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 补货单明细Service业务层处理
 *
 * @author YRT
 * @date 2024-08-23
 */
@RequiredArgsConstructor
@Service
public class StorageReplenishmentDetailServiceImpl extends ServiceImplPlus<StorageReplenishmentDetailMapper, StorageReplenishmentDetail, StorageReplenishmentDetailVo, StorageReplenishmentDetailBo> implements IStorageReplenishmentDetailService {
  /**
   * 根据主表ID获取明细集合
   *
   * @param mainId
   * @return 返回明细集合
   */
  @Override
  public List<StorageReplenishmentDetail> selectListByMainId(Long mainId) {
    LambdaQueryWrapper<StorageReplenishmentDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
    detailLambdaQueryWrapper.eq(StorageReplenishmentDetail::getReplenishmentId, mainId);
    return this.list(detailLambdaQueryWrapper);
  }

  /**
   * 获取补货数据
   *
   * @param map
   * @return R 返回保存结果
   */
  @Override
  public R<Map<String, Object>> getReplenishmentGroupData(Map<String, Object> map) {
    String replenishmentCode = Convert.toStr(map.get("replenishmentCode"));
    // 获取扫描明细信息
    MPJLambdaWrapper<StorageReplenishmentDetail> detailMPJLambdaWrapper = new MPJLambdaWrapper<>();
    detailMPJLambdaWrapper
      .select(StorageReplenishment::getReplenishmentCode)
      .select(CoreInventoryHolder::getProductId, CoreInventoryHolder::getProductCode, CoreInventoryHolder::getProductModel, CoreInventoryHolder::getProductName, CoreInventoryHolder::getProductSpec, CoreInventoryHolder::getStorageId, CoreInventoryHolder::getStorageName, CoreInventoryHolder::getBatchNumber, CoreInventoryHolder::getProduceDate, CoreInventoryHolder::getPlateCode, CoreInventoryHolder::getPositionName)
      .select(StorageReplenishmentDetail::getReplenishmentId, StorageReplenishmentDetail::getReplenishmentDetailId)
      .select("t.position_name AS targetPositionName")
      .selectSum(CoreInventoryHolder::getHolderStorage)


      .innerJoin(StorageReplenishment.class, StorageReplenishment::getReplenishmentId, StorageReplenishmentDetail::getReplenishmentId)

      .innerJoin(CoreInventoryHolder.class, on -> {
        on.eq(StorageReplenishmentDetail::getReplenishmentId, CoreInventoryHolder::getMainId)
          .eq(StorageReplenishmentDetail::getReplenishmentDetailId, CoreInventoryHolder::getDetailId);
        return on;
      })

      .gt(CoreInventoryHolder::getHolderStorage, 0) // 剩余占位量>0
      .eq(CoreInventoryHolder::getSourceType, HolderSourceTypeEnum.PC_REPLENISHMENT.getName()) // pc补货
      .eq(StorageReplenishment::getReplenishmentCode, replenishmentCode)

      .groupBy(CoreInventoryHolder::getProductId, CoreInventoryHolder::getProductCode, CoreInventoryHolder::getProductModel, CoreInventoryHolder::getProductName, CoreInventoryHolder::getProductSpec, CoreInventoryHolder::getPositionName, CoreInventoryHolder::getStorageId, CoreInventoryHolder::getStorageName, CoreInventoryHolder::getBatchNumber, CoreInventoryHolder::getProduceDate, CoreInventoryHolder::getPlateCode, CoreInventoryHolder::getPositionName)

      .groupBy(StorageReplenishmentDetail::getPositionName, StorageReplenishmentDetail::getReplenishmentId, StorageReplenishmentDetail::getReplenishmentDetailId);


    List<Map<String, Object>> detailList = this.selectJoinMaps(detailMPJLambdaWrapper);
    Assert.isFalse(detailList.isEmpty(), "没有可用的商品明细！");

    // 返回数据
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("detailList", detailList);

    return R.ok(resultMap);
  }
}

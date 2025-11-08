package com.yiruantong.composite.service.basic.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageAreaService;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.composite.service.basic.IBaseStorageAreaCompositeService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 库区操作
 */
@RequiredArgsConstructor
@Service
public class BaseStorageAreaCompositeServiceImpl implements IBaseStorageAreaCompositeService {
  private final ICoreInventoryService coreInventoryService;
  private final IBaseStorageAreaService baseStorageAreaService;
  private final IBasePositionService basePositionService;

  /**
   * 删除前事件
   * @param Ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public int deleteByIds(Long[] Ids) {
    for (Long id : Ids) {
      // 查询当前库区信息
      BaseStorageArea baseStorageArea = baseStorageAreaService.getById(id);

      // 查询当前仓库下的库存数据
      MPJLambdaWrapper<CoreInventory> wrapper = new MPJLambdaWrapper<>();
      wrapper.selectAll(CoreInventory.class)
        .innerJoin(BasePosition.class, on->{
          on.eq(BasePosition::getStorageId, CoreInventory::getStorageId)
            .eq(BasePosition::getPositionName, CoreInventory::getPositionName);
          return on;
        })
        .eq(BasePosition::getAreaCode, baseStorageArea.getAreaCode())
        .eq(BasePosition::getStorageId, baseStorageArea.getStorageId())
        .gt(CoreInventory::getProductStorage, BigDecimal.ZERO) //库存量>0
        .last("LIMIT 1");
      var coreList = coreInventoryService.selectList(wrapper);

      if (!coreList.isEmpty()) {
        throw new ServiceException("当前库区【" + baseStorageArea.getAreaCode() + "】存在库存，不允许删除！");
      }

      // 删除当前库区下的货位数据
      LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
      positionLambdaQueryWrapper  .eq(BasePosition::getAreaCode, baseStorageArea.getAreaCode())
        .eq(BasePosition::getStorageId, baseStorageArea.getStorageId());
      basePositionService.remove(positionLambdaQueryWrapper);
    }

    return baseStorageAreaService.deleteByIds(Ids);
  }
}

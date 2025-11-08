package com.yiruantong.composite.service.basic.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.composite.service.basic.IBasePositionCompositeService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 仓库信息操作
 */
@RequiredArgsConstructor
@Service
public class BasePositionCompositeServiceImpl implements IBasePositionCompositeService {
  private final IBasePositionService basePositionService;
  private final ICoreInventoryService coreInventoryService;

  /**
   * 删除前事件
   * @param Ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public int deleteByIds(Long[] Ids) {
    for (Long id : Ids) {
      // 查询当前货位信息
      BasePosition basePosition = basePositionService.getById(id);

      // 查询当前货位下的库存数据
      LambdaQueryWrapper<CoreInventory> coreInventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
      coreInventoryLambdaQueryWrapper.eq(CoreInventory::getPositionName, basePosition.getPositionName())
        .eq(CoreInventory::getStorageId, basePosition.getStorageId())
      .gt(CoreInventory::getProductStorage, BigDecimal.ZERO);
      var coreList = coreInventoryService.selectList(coreInventoryLambdaQueryWrapper);
      if (coreList.size() > 0) {
        throw new ServiceException("当前货位【" + basePosition.getPositionName() + "】存在库存，不允许删除！");
      }

    }

    return basePositionService.deleteByIds(Ids);
  }
}

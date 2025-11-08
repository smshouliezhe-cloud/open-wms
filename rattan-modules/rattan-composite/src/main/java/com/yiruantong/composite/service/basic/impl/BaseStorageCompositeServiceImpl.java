package com.yiruantong.composite.service.basic.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.RequiredArgsConstructor;
import com.yiruantong.basic.domain.storage.BasePosition;
import com.yiruantong.basic.domain.storage.BaseStorage;
import com.yiruantong.basic.domain.storage.BaseStorageArea;
import com.yiruantong.basic.service.storage.IBasePositionService;
import com.yiruantong.basic.service.storage.IBaseStorageAreaService;
import com.yiruantong.basic.service.storage.IBaseStorageService;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.exception.ServiceException;
import com.yiruantong.common.core.utils.StringUtils;
import com.yiruantong.common.satoken.utils.LoginHelper;
import com.yiruantong.composite.service.basic.IBaseStorageCompositeService;
import com.yiruantong.inventory.domain.core.CoreInventory;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 仓库信息操作
 */
@RequiredArgsConstructor
@Service
public class BaseStorageCompositeServiceImpl implements IBaseStorageCompositeService {
  private final IBaseStorageService baseStorageService;
  private final ICoreInventoryService coreInventoryService;
  private final IBaseStorageAreaService baseStorageAreaService;
  private final IBasePositionService basePositionService;

  /**
   * 删除前事件
   *
   * @param Ids
   * @return
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public int deleteByIds(Long[] Ids) {
    for (Long id : Ids) {
      // 查询当前仓库信息
      BaseStorage baseStorage = baseStorageService.getById(id);

      // 查询当前仓库下的库存数据
      LambdaQueryWrapper<CoreInventory> coreInventoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
      coreInventoryLambdaQueryWrapper.eq(CoreInventory::getStorageId, baseStorage.getStorageId())
        .gt(CoreInventory::getProductStorage, BigDecimal.ZERO);
      var coreList = coreInventoryService.selectList(coreInventoryLambdaQueryWrapper);
      if (!coreList.isEmpty()) {
        throw new ServiceException("当前仓库【" + baseStorage.getStorageName() + "】存在库存，不允许删除！");
      }

      // 删除当前仓库下的库区数据
      LambdaQueryWrapper<BaseStorageArea> storageAreaLambdaQueryWrapper = new LambdaQueryWrapper<>();
      storageAreaLambdaQueryWrapper.eq(BaseStorageArea::getStorageId, baseStorage.getStorageId());
      baseStorageAreaService.remove(storageAreaLambdaQueryWrapper);

      // 删除当前仓库下的货位数据
      LambdaQueryWrapper<BasePosition> positionLambdaQueryWrapper = new LambdaQueryWrapper<>();
      positionLambdaQueryWrapper.eq(BasePosition::getStorageId, baseStorage.getStorageId());
      basePositionService.remove(positionLambdaQueryWrapper);
    }

    return baseStorageService.deleteByIds(Ids);
  }

  @Override
  public R<List<Map<String, Object>>> getGridData() {
    String sql = StringUtils.format("""
      SELECT storage_name, CASE WHEN positionCount=0 THEN 0 ELSE 1.0 * usingCount/positionCount END AS positionUsingRate  FROM (
      SELECT storage_name,
      (SELECT COUNT(1) FROM base_position p WHERE p.storage_id=o.storage_id) AS positionCount,
      (SELECT COUNT(DISTINCT pp.position_name) FROM core_inventory pp WHERE pp.storage_id=o.storage_id AND pp.product_storage>0) AS usingCount
      FROM base_storage o
      ) t
      """);
    List<Map<String, Object>> maps = SqlRunner.db().selectList(sql);
    return R.ok(maps);
  }
}

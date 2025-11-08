package com.yiruantong.composite.service.bigScreen;

import com.yiruantong.common.core.domain.R;

import java.util.Map;

/**
 * PC首页 pc-stat统计信息
 */
public interface HomeService {
  /**
   * 查询库存预警
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> inventoryAlert(Map<String, Object> map);

  /**
   * 查询库存状况
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> inventoryStatus(Map<String, Object> map);

  /**
   * 查询库存金额（饼图）
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> inventoryAmount(Map<String, Object> map);

  /**
   * 仓库使用状况
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> storageStatus(Map<String, Object> map);

  /**
   * 货位库存统计
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> inventoryStatistics(Map<String, Object> map);

  /**
   * 库存商品类别
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  R<Map<String, Object>> inventoryProductType(Map<String, Object> map);
}

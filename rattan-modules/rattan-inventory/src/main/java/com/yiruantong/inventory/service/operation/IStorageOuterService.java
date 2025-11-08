package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.inventory.domain.operation.api.ApiStorageOuterBo;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterBo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterVo;

import java.util.List;
import java.util.Map;

/**
 * 其他出库单Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStorageOuterService extends IServicePlus<StorageOuter, StorageOuterVo, StorageOuterBo> {
  /**
   * 获取分拣列表
   *
   * @param map
   * @return
   */
  List<StorageOuterSortingRule> getSortingRule(Map<String, Object> map);

  /**
   * 提交分拣规则
   *
   * @param map
   */
  R<Void> setSortingRule(Map<String, Object> map);

  /**
   * 关闭分拣规则
   *
   * @param map
   */
  R<Void> deleteSortingRule(Map<String, Object> map);

  /**
   * 新增数据
   */
  R<Map<String, Object>> add(ApiStorageOuterBo bo);
}

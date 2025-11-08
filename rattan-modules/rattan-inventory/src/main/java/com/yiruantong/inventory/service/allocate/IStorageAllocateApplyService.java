package com.yiruantong.inventory.service.allocate;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyVo;

import java.util.List;
import java.util.Map;

/**
 * 调拨申请单Service接口
 *
 * @author YRT
 * @date 2023-12-19
 */
public interface IStorageAllocateApplyService extends IServicePlus<StorageAllocateApply, StorageAllocateApplyVo, StorageAllocateApplyBo> {
  boolean updateAllocateStatus(StorageAllocateApply storageAllocateApplyInfo, SortingStatusEnum sortingStatus);


  /**
   * 获取分拣列表
   *
   * @param map
   * @return
   */
  List<ApplySortingRule> getSortingRule(Map<String, Object> map);

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
  StorageAllocateApply calibrationApply(String sourceId, String sourceCode,String type);
}

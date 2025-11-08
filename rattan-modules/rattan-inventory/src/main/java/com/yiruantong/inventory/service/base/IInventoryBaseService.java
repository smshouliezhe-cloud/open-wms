package com.yiruantong.inventory.service.base;

import com.yiruantong.common.core.enums.base.SortingStatusEnum;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;

import java.util.List;

/**
 * 库存分拣基础接口
 */
public interface IInventoryBaseService {
  /**
   * 计算缺货数量
   *
   * @param detailList 明细集合
   */
  default void updateLackStorage(List<CommonDetailDto> detailList) {
  }

  /**
   * 更新分拣状态
   *
   * @param mainID            主表ID
   * @param sortingStatusEnum 分拣状态
   */
  default void updateSortingStatus(Long mainID, SortingStatusEnum sortingStatusEnum) {
  }

  /**
   * 自定义分拣过滤器
   *
   * @param inventoryList 可分拣库存集合
   * @return 返回过滤数据
   */
  default List<CoreInventoryComposeVo> customSortingFilter(CommonMainDto mainInfo, CommonDetailDto detailInfo, List<CoreInventoryComposeVo> inventoryList) {
    return inventoryList;
  }

}

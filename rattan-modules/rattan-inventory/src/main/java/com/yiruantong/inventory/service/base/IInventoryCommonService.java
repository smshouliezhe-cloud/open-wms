package com.yiruantong.inventory.service.base;

import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.enums.base.HolderSourceTypeEnum;
import com.yiruantong.common.core.enums.base.InventorySourceTypeEnum;
import com.yiruantong.inventory.domain.base.dto.CommonDetailDto;
import com.yiruantong.inventory.domain.base.dto.CommonMainDto;

import java.util.List;

public interface IInventoryCommonService {
  /**
   * 设置当前业务模块Service
   * @param baseService 业务模块service
   */
  void setBizService(IInventoryBaseService baseService);

  /**
   * 出库分拣
   * @param mainInfo 主表信息
   * @param detailList 明细表信息集合
   * @param holderSourceTypeEnum 占位类型
   * @return R
   */
  R<List<CommonDetailDto>> sorting(CommonMainDto mainInfo, List<CommonDetailDto> detailList, HolderSourceTypeEnum holderSourceTypeEnum);

  /**
   * 常规出库操作
   * @param mainInfo 主表信息
   * @param detailList 明细表信息集合
   * @param inventorySourceTypeEnum 库存来源信息
   */
  R<List<CommonDetailDto>> out(CommonMainDto mainInfo, List<CommonDetailDto> detailList, InventorySourceTypeEnum inventorySourceTypeEnum);

  /**
   * 入库操作
   *
   * @param mainInfo                主表信息
   * @param detailList              明细表信息集合
   * @param inventorySourceTypeEnum 库存来源信息
   * @return 返回入库数据
   */
  R<List<CommonDetailDto>> in(CommonMainDto mainInfo, List<CommonDetailDto> detailList, InventorySourceTypeEnum inventorySourceTypeEnum);
}

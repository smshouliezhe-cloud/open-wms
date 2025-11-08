package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StoragePurchasePriceAdjustDetailBo;

import java.util.List;

/**
 * 库存成本价调整明细Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStoragePurchasePriceAdjustDetailService extends IServicePlus<StoragePurchasePriceAdjustDetail, StoragePurchasePriceAdjustDetailVo, StoragePurchasePriceAdjustDetailBo> {
  List<StoragePurchasePriceAdjustDetail> selectListByMainId(Long id);

  /**
   * 库存成本价调整明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  TableDataInfo<StoragePurchasePriceAdjustDetailComposeVo> selectPurchasePriceAdjustDetailComposeList(PageQuery pageQuery);
}

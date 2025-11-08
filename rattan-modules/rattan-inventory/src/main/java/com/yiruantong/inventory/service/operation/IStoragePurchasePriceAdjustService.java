package com.yiruantong.inventory.service.operation;

import com.yiruantong.common.mybatis.core.service.IServicePlus;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjust;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StoragePurchasePriceAdjustBo;

/**
 * 库存成本价调整Service接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface IStoragePurchasePriceAdjustService extends IServicePlus<StoragePurchasePriceAdjust, StoragePurchasePriceAdjustVo, StoragePurchasePriceAdjustBo> {
}

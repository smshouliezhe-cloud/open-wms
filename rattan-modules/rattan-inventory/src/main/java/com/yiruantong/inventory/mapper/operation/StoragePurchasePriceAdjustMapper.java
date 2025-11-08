package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjust;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 库存成本价调整Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StoragePurchasePriceAdjustMapper extends BaseMapperPlus<StoragePurchasePriceAdjust, StoragePurchasePriceAdjustVo> {

}

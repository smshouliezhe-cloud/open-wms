package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 库存成本价调整明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StoragePurchasePriceAdjustDetailMapper extends BaseMapperPlus<StoragePurchasePriceAdjustDetail, StoragePurchasePriceAdjustDetailVo> {

}

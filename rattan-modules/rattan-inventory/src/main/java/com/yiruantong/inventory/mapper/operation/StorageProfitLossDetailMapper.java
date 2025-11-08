package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 盈亏单明细Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageProfitLossDetailMapper extends BaseMapperPlus<StorageProfitLossDetail, StorageProfitLossDetailVo> {

}

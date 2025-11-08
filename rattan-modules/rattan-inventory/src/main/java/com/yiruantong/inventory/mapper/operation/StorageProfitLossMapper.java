package com.yiruantong.inventory.mapper.operation;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.operation.StorageProfitLoss;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 盈亏单Mapper接口
 *
 * @author YRT
 * @date 2023-10-24
 */
public interface StorageProfitLossMapper extends BaseMapperPlus<StorageProfitLoss, StorageProfitLossVo> {

}

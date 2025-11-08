package com.yiruantong.inventory.mapper.replenishment;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishmentDetail;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 补货单明细Mapper接口
 *
 * @author YRT
 * @date 2024-08-23
 */
public interface StorageReplenishmentDetailMapper extends BaseMapperPlus<StorageReplenishmentDetail, StorageReplenishmentDetailVo> {

}

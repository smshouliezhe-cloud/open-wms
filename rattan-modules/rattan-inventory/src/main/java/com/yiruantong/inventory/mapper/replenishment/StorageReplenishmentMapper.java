package com.yiruantong.inventory.mapper.replenishment;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 补货单Mapper接口
 *
 * @author YRT
 * @date 2024-08-23
 */
public interface StorageReplenishmentMapper extends BaseMapperPlus<StorageReplenishment, StorageReplenishmentVo> {

}

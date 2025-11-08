package com.yiruantong.inventory.mapper.allocate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnter;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 调拨入库单Mapper接口
 *
 * @author YRT
 * @date 2023-12-20
 */
public interface StorageAllocateEnterMapper extends BaseMapperPlus<StorageAllocateEnter, StorageAllocateEnterVo> {

}

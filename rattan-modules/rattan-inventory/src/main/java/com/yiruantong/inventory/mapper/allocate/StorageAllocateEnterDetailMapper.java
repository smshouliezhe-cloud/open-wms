package com.yiruantong.inventory.mapper.allocate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnterDetail;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 调拨入库单明细Mapper接口
 *
 * @author YRT
 * @date 2023-12-20
 */
public interface StorageAllocateEnterDetailMapper extends BaseMapperPlus<StorageAllocateEnterDetail, StorageAllocateEnterDetailVo> {

}

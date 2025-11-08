package com.yiruantong.inventory.mapper.allocate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 调拨申请单Mapper接口
 *
 * @author YRT
 * @date 2023-12-19
 */
public interface StorageAllocateApplyMapper extends BaseMapperPlus<StorageAllocateApply, StorageAllocateApplyVo> {

}

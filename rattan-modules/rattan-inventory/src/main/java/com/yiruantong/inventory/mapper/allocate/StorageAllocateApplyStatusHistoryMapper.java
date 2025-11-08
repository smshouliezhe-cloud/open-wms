package com.yiruantong.inventory.mapper.allocate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyStatusHistory;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyStatusHistoryVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 调拨申请单轨迹Mapper接口
 *
 * @author YRT
 * @date 2023-12-22
 */
public interface StorageAllocateApplyStatusHistoryMapper extends BaseMapperPlus<StorageAllocateApplyStatusHistory, StorageAllocateApplyStatusHistoryVo> {

}

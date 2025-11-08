package com.yiruantong.inventory.mapper.allocate;

import org.apache.ibatis.annotations.Mapper;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyDetailVo;
import com.yiruantong.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 调拨申请单明细Mapper接口
 *
 * @author YRT
 * @date 2023-12-19
 */
public interface StorageAllocateApplyDetailMapper extends BaseMapperPlus<StorageAllocateApplyDetail, StorageAllocateApplyDetailVo> {

}

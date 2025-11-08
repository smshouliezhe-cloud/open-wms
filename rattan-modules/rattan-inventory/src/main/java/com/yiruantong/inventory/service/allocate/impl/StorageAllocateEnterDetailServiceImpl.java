package com.yiruantong.inventory.service.allocate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateEnterDetailBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterDetailVo;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnterDetail;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateEnterDetailMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateEnterDetailService;

/**
 * 调拨入库单明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-20
 */
@RequiredArgsConstructor
@Service
public class StorageAllocateEnterDetailServiceImpl extends ServiceImplPlus<StorageAllocateEnterDetailMapper, StorageAllocateEnterDetail, StorageAllocateEnterDetailVo, StorageAllocateEnterDetailBo> implements IStorageAllocateEnterDetailService {
}

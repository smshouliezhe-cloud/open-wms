package com.yiruantong.inventory.service.allocate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateEnterBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterVo;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnter;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateEnterMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateEnterService;

/**
 * 调拨入库单Service业务层处理
 *
 * @author YRT
 * @date 2023-12-20
 */
@RequiredArgsConstructor
@Service
public class StorageAllocateEnterServiceImpl extends ServiceImplPlus<StorageAllocateEnterMapper, StorageAllocateEnter, StorageAllocateEnterVo, StorageAllocateEnterBo> implements IStorageAllocateEnterService {
}

package com.yiruantong.inventory.service.operation.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleBo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleVo;
import com.yiruantong.inventory.domain.operation.StorageAssemble;
import com.yiruantong.inventory.mapper.operation.StorageAssembleMapper;
import com.yiruantong.inventory.service.operation.IStorageAssembleService;

/**
 * 商品拆装单Service业务层处理
 *
 * @author YRT
 * @date 2023-10-24
 */
@RequiredArgsConstructor
@Service
public class StorageAssembleServiceImpl extends ServiceImplPlus<StorageAssembleMapper, StorageAssemble, StorageAssembleVo, StorageAssembleBo> implements IStorageAssembleService {
}

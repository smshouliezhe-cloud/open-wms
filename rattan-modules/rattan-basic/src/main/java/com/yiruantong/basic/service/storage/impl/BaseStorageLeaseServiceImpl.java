package com.yiruantong.basic.service.storage.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.storage.bo.BaseStorageLeaseBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageLeaseVo;
import com.yiruantong.basic.domain.storage.BaseStorageLease;
import com.yiruantong.basic.mapper.storage.BaseStorageLeaseMapper;
import com.yiruantong.basic.service.storage.IBaseStorageLeaseService;

/**
 * 租赁管理Service业务层处理
 *
 * @author YRT
 * @date 2024-03-09
 */
@RequiredArgsConstructor
@Service
public class BaseStorageLeaseServiceImpl extends ServiceImplPlus<BaseStorageLeaseMapper, BaseStorageLease, BaseStorageLeaseVo, BaseStorageLeaseBo> implements IBaseStorageLeaseService {
}

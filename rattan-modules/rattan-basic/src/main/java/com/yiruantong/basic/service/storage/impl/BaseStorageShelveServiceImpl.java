package com.yiruantong.basic.service.storage.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.storage.bo.BaseStorageShelveBo;
import com.yiruantong.basic.domain.storage.vo.BaseStorageShelveVo;
import com.yiruantong.basic.domain.storage.BaseStorageShelve;
import com.yiruantong.basic.mapper.storage.BaseStorageShelveMapper;
import com.yiruantong.basic.service.storage.IBaseStorageShelveService;

/**
 * 仓库货架Service业务层处理
 *
 * @author YRT
 * @date 2024-02-22
 */
@RequiredArgsConstructor
@Service
public class BaseStorageShelveServiceImpl extends ServiceImplPlus<BaseStorageShelveMapper, BaseStorageShelve, BaseStorageShelveVo, BaseStorageShelveBo> implements IBaseStorageShelveService {
}

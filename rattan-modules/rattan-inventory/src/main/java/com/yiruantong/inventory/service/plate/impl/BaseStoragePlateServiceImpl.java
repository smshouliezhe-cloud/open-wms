package com.yiruantong.inventory.service.plate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.plate.bo.BaseStoragePlateBo;
import com.yiruantong.inventory.domain.plate.vo.BaseStoragePlateVo;
import com.yiruantong.inventory.domain.plate.BaseStoragePlate;
import com.yiruantong.inventory.mapper.plate.BaseStoragePlateMapper;
import com.yiruantong.inventory.service.plate.IBaseStoragePlateService;

/**
 * 仓库容器查询Service业务层处理
 *
 * @author YRT
 * @date 2024-03-06
 */
@RequiredArgsConstructor
@Service
public class BaseStoragePlateServiceImpl extends ServiceImplPlus<BaseStoragePlateMapper, BaseStoragePlate, BaseStoragePlateVo, BaseStoragePlateBo> implements IBaseStoragePlateService {
}

package com.yiruantong.inventory.service.plate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.plate.bo.BasePlateClientBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateClientVo;
import com.yiruantong.inventory.domain.plate.BasePlateClient;
import com.yiruantong.inventory.mapper.plate.BasePlateClientMapper;
import com.yiruantong.inventory.service.plate.IBasePlateClientService;

/**
 * 客户容器管理Service业务层处理
 *
 * @author YRT
 * @date 2023-12-21
 */
@RequiredArgsConstructor
@Service
public class BasePlateClientServiceImpl extends ServiceImplPlus<BasePlateClientMapper, BasePlateClient, BasePlateClientVo, BasePlateClientBo> implements IBasePlateClientService {
}

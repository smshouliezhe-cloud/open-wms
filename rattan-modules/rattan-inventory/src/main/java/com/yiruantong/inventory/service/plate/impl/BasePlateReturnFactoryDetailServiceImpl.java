package com.yiruantong.inventory.service.plate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnFactoryDetailBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnFactoryDetailVo;
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactoryDetail;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnFactoryDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnFactoryDetailService;

/**
 * 容器返厂单明细Service业务层处理
 *
 * @author YRT
 * @date 2024-03-05
 */
@RequiredArgsConstructor
@Service
public class BasePlateReturnFactoryDetailServiceImpl extends ServiceImplPlus<BasePlateReturnFactoryDetailMapper, BasePlateReturnFactoryDetail, BasePlateReturnFactoryDetailVo, BasePlateReturnFactoryDetailBo> implements IBasePlateReturnFactoryDetailService {
}

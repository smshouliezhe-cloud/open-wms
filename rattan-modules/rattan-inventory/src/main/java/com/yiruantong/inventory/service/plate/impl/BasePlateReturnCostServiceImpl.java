package com.yiruantong.inventory.service.plate.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnCostBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnCostVo;
import com.yiruantong.inventory.domain.plate.BasePlateReturnCost;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnCostMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnCostService;

/**
 * 容器返厂费用明细Service业务层处理
 *
 * @author YRT
 * @date 2024-03-14
 */
@RequiredArgsConstructor
@Service
public class BasePlateReturnCostServiceImpl extends ServiceImplPlus<BasePlateReturnCostMapper, BasePlateReturnCost, BasePlateReturnCostVo, BasePlateReturnCostBo> implements IBasePlateReturnCostService {
}

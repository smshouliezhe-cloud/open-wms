package com.yiruantong.basic.service.base.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.base.bo.BaseConsignorSalesBo;
import com.yiruantong.basic.domain.base.vo.BaseConsignorSalesVo;
import com.yiruantong.basic.domain.base.BaseConsignorSales;
import com.yiruantong.basic.mapper.base.BaseConsignorSalesMapper;
import com.yiruantong.basic.service.base.IBaseConsignorSalesService;

/**
 * 门店明细Service业务层处理
 *
 * @author YRT
 * @date 2024-12-28
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorSalesServiceImpl extends ServiceImplPlus<BaseConsignorSalesMapper, BaseConsignorSales, BaseConsignorSalesVo, BaseConsignorSalesBo> implements IBaseConsignorSalesService {
}

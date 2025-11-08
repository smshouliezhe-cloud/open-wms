package com.yiruantong.basic.service.base.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.basic.domain.base.bo.BaseConsignorSalesLevelBo;
import com.yiruantong.basic.domain.base.vo.BaseConsignorSalesLevelVo;
import com.yiruantong.basic.domain.base.BaseConsignorSalesLevel;
import com.yiruantong.basic.mapper.base.BaseConsignorSalesLevelMapper;
import com.yiruantong.basic.service.base.IBaseConsignorSalesLevelService;

/**
 * 门店销售等级设置Service业务层处理
 *
 * @author YRT
 * @date 2025-01-07
 */
@RequiredArgsConstructor
@Service
public class BaseConsignorSalesLevelServiceImpl extends ServiceImplPlus<BaseConsignorSalesLevelMapper, BaseConsignorSalesLevel, BaseConsignorSalesLevelVo, BaseConsignorSalesLevelBo> implements IBaseConsignorSalesLevelService {
}

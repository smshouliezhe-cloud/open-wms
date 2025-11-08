package com.yiruantong.inbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.service.bo.InExchangeBo;
import com.yiruantong.inbound.domain.service.vo.InExchangeVo;
import com.yiruantong.inbound.domain.service.InExchange;
import com.yiruantong.inbound.mapper.service.InExchangeMapper;
import com.yiruantong.inbound.service.service.IInExchangeService;

/**
 * 换货单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InExchangeServiceImpl extends ServiceImplPlus<InExchangeMapper, InExchange, InExchangeVo, InExchangeBo> implements IInExchangeService {
}

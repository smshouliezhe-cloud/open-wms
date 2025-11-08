package com.yiruantong.inbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.service.bo.InExchangeOuterDetailBo;
import com.yiruantong.inbound.domain.service.vo.InExchangeOuterDetailVo;
import com.yiruantong.inbound.domain.service.InExchangeOuterDetail;
import com.yiruantong.inbound.mapper.service.InExchangeOuterDetailMapper;
import com.yiruantong.inbound.service.service.IInExchangeOuterDetailService;

/**
 * 换货单出库明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InExchangeOuterDetailServiceImpl extends ServiceImplPlus<InExchangeOuterDetailMapper, InExchangeOuterDetail, InExchangeOuterDetailVo, InExchangeOuterDetailBo> implements IInExchangeOuterDetailService {
}

package com.yiruantong.outbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.service.bo.OutExchangeOuterDetailBo;
import com.yiruantong.outbound.domain.service.vo.OutExchangeOuterDetailVo;
import com.yiruantong.outbound.domain.service.OutExchangeOuterDetail;
import com.yiruantong.outbound.mapper.service.OutExchangeOuterDetailMapper;
import com.yiruantong.outbound.service.service.IOutExchangeOuterDetailService;

/**
 * 换货管理Outer明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2024-11-21
 */
@RequiredArgsConstructor
@Service
public class OutExchangeOuterDetailServiceImpl extends ServiceImplPlus<OutExchangeOuterDetailMapper, OutExchangeOuterDetail, OutExchangeOuterDetailVo, OutExchangeOuterDetailBo> implements IOutExchangeOuterDetailService {
}

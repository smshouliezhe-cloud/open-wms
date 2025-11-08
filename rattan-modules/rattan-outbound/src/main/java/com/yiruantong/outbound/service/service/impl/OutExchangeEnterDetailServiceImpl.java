package com.yiruantong.outbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.service.bo.OutExchangeEnterDetailBo;
import com.yiruantong.outbound.domain.service.vo.OutExchangeEnterDetailVo;
import com.yiruantong.outbound.domain.service.OutExchangeEnterDetail;
import com.yiruantong.outbound.mapper.service.OutExchangeEnterDetailMapper;
import com.yiruantong.outbound.service.service.IOutExchangeEnterDetailService;

/**
 * 换货管理Enter明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2024-11-21
 */
@RequiredArgsConstructor
@Service
public class OutExchangeEnterDetailServiceImpl extends ServiceImplPlus<OutExchangeEnterDetailMapper, OutExchangeEnterDetail, OutExchangeEnterDetailVo, OutExchangeEnterDetailBo> implements IOutExchangeEnterDetailService {
}

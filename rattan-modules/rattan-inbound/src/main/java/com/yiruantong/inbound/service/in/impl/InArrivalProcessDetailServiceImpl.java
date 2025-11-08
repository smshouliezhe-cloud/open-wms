package com.yiruantong.inbound.service.in.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.in.bo.InArrivalProcessDetailBo;
import com.yiruantong.inbound.domain.in.vo.InArrivalProcessDetailVo;
import com.yiruantong.inbound.domain.in.InArrivalProcessDetail;
import com.yiruantong.inbound.mapper.in.InArrivalProcessDetailMapper;
import com.yiruantong.inbound.service.in.IInArrivalProcessDetailService;

/**
 * 到货加工明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@RequiredArgsConstructor
@Service
public class InArrivalProcessDetailServiceImpl extends ServiceImplPlus<InArrivalProcessDetailMapper, InArrivalProcessDetail, InArrivalProcessDetailVo, InArrivalProcessDetailBo> implements IInArrivalProcessDetailService {
}

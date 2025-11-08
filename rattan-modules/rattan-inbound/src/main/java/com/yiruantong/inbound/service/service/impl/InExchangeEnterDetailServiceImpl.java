package com.yiruantong.inbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.service.bo.InExchangeEnterDetailBo;
import com.yiruantong.inbound.domain.service.vo.InExchangeEnterDetailVo;
import com.yiruantong.inbound.domain.service.InExchangeEnterDetail;
import com.yiruantong.inbound.mapper.service.InExchangeEnterDetailMapper;
import com.yiruantong.inbound.service.service.IInExchangeEnterDetailService;

/**
 * 换货单入库明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InExchangeEnterDetailServiceImpl extends ServiceImplPlus<InExchangeEnterDetailMapper, InExchangeEnterDetail, InExchangeEnterDetailVo, InExchangeEnterDetailBo> implements IInExchangeEnterDetailService {
}

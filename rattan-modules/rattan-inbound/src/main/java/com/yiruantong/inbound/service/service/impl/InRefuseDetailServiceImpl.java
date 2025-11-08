package com.yiruantong.inbound.service.service.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.inbound.domain.service.bo.InRefuseDetailBo;
import com.yiruantong.inbound.domain.service.vo.InRefuseDetailVo;
import com.yiruantong.inbound.domain.service.InRefuseDetail;
import com.yiruantong.inbound.mapper.service.InRefuseDetailMapper;
import com.yiruantong.inbound.service.service.IInRefuseDetailService;

/**
 * 拒收单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@RequiredArgsConstructor
@Service
public class InRefuseDetailServiceImpl extends ServiceImplPlus<InRefuseDetailMapper, InRefuseDetail, InRefuseDetailVo, InRefuseDetailBo> implements IInRefuseDetailService {
}

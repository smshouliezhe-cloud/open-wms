package com.yiruantong.outbound.service.order.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.order.bo.OutRetailDetailBo;
import com.yiruantong.outbound.domain.order.vo.OutRetailDetailVo;
import com.yiruantong.outbound.domain.order.OutRetailDetail;
import com.yiruantong.outbound.mapper.order.OutRetailDetailMapper;
import com.yiruantong.outbound.service.order.IOutRetailDetailService;

/**
 * 订货单明细Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@RequiredArgsConstructor
@Service
public class OutRetailDetailServiceImpl extends ServiceImplPlus<OutRetailDetailMapper, OutRetailDetail, OutRetailDetailVo, OutRetailDetailBo> implements IOutRetailDetailService {
}

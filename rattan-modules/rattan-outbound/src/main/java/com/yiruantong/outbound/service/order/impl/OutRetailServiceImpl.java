package com.yiruantong.outbound.service.order.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.order.bo.OutRetailBo;
import com.yiruantong.outbound.domain.order.vo.OutRetailVo;
import com.yiruantong.outbound.domain.order.OutRetail;
import com.yiruantong.outbound.mapper.order.OutRetailMapper;
import com.yiruantong.outbound.service.order.IOutRetailService;

/**
 * 订货单Service业务层处理
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@RequiredArgsConstructor
@Service
public class OutRetailServiceImpl extends ServiceImplPlus<OutRetailMapper, OutRetail, OutRetailVo, OutRetailBo> implements IOutRetailService {
}

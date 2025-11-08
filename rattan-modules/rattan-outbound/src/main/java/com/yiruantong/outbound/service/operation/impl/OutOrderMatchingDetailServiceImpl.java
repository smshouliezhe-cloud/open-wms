package com.yiruantong.outbound.service.operation.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.operation.bo.OutOrderMatchingDetailBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingDetailVo;
import com.yiruantong.outbound.domain.operation.OutOrderMatchingDetail;
import com.yiruantong.outbound.mapper.operation.OutOrderMatchingDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderMatchingDetailService;

/**
 * 订单配货明细Service业务层处理
 *
 * @author YRT
 * @date 2023-12-09
 */
@RequiredArgsConstructor
@Service
public class OutOrderMatchingDetailServiceImpl extends ServiceImplPlus<OutOrderMatchingDetailMapper, OutOrderMatchingDetail, OutOrderMatchingDetailVo, OutOrderMatchingDetailBo> implements IOutOrderMatchingDetailService {
}

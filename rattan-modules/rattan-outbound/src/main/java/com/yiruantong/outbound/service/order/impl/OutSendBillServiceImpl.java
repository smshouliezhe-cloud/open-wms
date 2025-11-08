package com.yiruantong.outbound.service.order.impl;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.service.ServiceImplPlus;
import org.springframework.stereotype.Service;
import com.yiruantong.outbound.domain.order.bo.OutSendBillBo;
import com.yiruantong.outbound.domain.order.vo.OutSendBillVo;
import com.yiruantong.outbound.domain.order.OutSendBill;
import com.yiruantong.outbound.mapper.order.OutSendBillMapper;
import com.yiruantong.outbound.service.order.IOutSendBillService;

/**
 * 发货单明细Service业务层处理
 *
 * @author YRT
 * @date 2024-01-06
 */
@RequiredArgsConstructor
@Service
public class OutSendBillServiceImpl extends ServiceImplPlus<OutSendBillMapper, OutSendBill, OutSendBillVo, OutSendBillBo> implements IOutSendBillService {
}

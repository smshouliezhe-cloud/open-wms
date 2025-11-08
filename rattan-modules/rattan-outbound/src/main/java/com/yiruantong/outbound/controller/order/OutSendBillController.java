package com.yiruantong.outbound.controller.order;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.order.OutSendBill;
import com.yiruantong.outbound.domain.order.vo.OutSendBillVo;
import com.yiruantong.outbound.domain.order.bo.OutSendBillBo;
import com.yiruantong.outbound.mapper.order.OutSendBillMapper;
import com.yiruantong.outbound.service.order.IOutSendBillService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发货单明细
 *
 * @author YRT
 * @date 2024-01-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/order/sendBill")
public class OutSendBillController extends AbstractController<OutSendBillMapper, OutSendBill, OutSendBillVo, OutSendBillBo> {
}

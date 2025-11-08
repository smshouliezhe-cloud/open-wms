package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InOrderStatusHistory;
import com.yiruantong.inbound.domain.in.vo.InOrderStatusHistoryVo;
import com.yiruantong.inbound.domain.in.bo.InOrderStatusHistoryBo;
import com.yiruantong.inbound.mapper.in.InOrderStatusHistoryMapper;
import com.yiruantong.inbound.service.in.IInOrderStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 采购订单流水记录
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/orderStatusHistory")
public class InOrderStatusHistoryController extends AbstractController<InOrderStatusHistoryMapper, InOrderStatusHistory, InOrderStatusHistoryVo, InOrderStatusHistoryBo> {
}

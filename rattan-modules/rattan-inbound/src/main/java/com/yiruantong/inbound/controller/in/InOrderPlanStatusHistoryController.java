package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InOrderPlanStatusHistory;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanStatusHistoryVo;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanStatusHistoryBo;
import com.yiruantong.inbound.mapper.in.InOrderPlanStatusHistoryMapper;
import com.yiruantong.inbound.service.in.IInOrderPlanStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入库计划状态轨迹
 *
 * @author YRT
 * @date 2024-09-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/orderPlanStatusHistory")
public class InOrderPlanStatusHistoryController extends AbstractController<InOrderPlanStatusHistoryMapper, InOrderPlanStatusHistory, InOrderPlanStatusHistoryVo, InOrderPlanStatusHistoryBo> {
}

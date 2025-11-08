package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutOrderPlanStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanStatusHistoryVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanStatusHistoryBo;
import com.yiruantong.outbound.mapper.out.OutOrderPlanStatusHistoryMapper;
import com.yiruantong.outbound.service.out.IOutOrderPlanStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库计划状态轨迹
 *
 * @author YRT
 * @date 2024-09-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderPlanStatusHistory")
public class OutOrderPlanStatusHistoryController extends AbstractController<OutOrderPlanStatusHistoryMapper, OutOrderPlanStatusHistory, OutOrderPlanStatusHistoryVo, OutOrderPlanStatusHistoryBo> {
}

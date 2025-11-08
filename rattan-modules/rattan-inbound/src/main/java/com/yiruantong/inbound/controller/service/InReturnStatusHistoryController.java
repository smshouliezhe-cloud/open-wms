package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InReturnStatusHistory;
import com.yiruantong.inbound.domain.service.vo.InReturnStatusHistoryVo;
import com.yiruantong.inbound.domain.service.bo.InReturnStatusHistoryBo;
import com.yiruantong.inbound.mapper.service.InReturnStatusHistoryMapper;
import com.yiruantong.inbound.service.service.IInReturnStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 退货单流水
 *
 * @author YRT
 * @date 2024-01-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/returnStatusHistory")
public class InReturnStatusHistoryController extends AbstractController<InReturnStatusHistoryMapper, InReturnStatusHistory, InReturnStatusHistoryVo, InReturnStatusHistoryBo> {
}

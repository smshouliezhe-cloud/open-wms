package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutOrderWaveStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderWaveStatusHistoryVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderWaveStatusHistoryBo;
import com.yiruantong.outbound.mapper.out.OutOrderWaveStatusHistoryMapper;
import com.yiruantong.outbound.service.out.IOutOrderWaveStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单状态历史记录
 *
 * @author YRT
 * @date 2023-11-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderWaveStatusHistory")
public class OutOrderWaveStatusHistoryController extends AbstractController<OutOrderWaveStatusHistoryMapper, OutOrderWaveStatusHistory, OutOrderWaveStatusHistoryVo, OutOrderWaveStatusHistoryBo> {
}

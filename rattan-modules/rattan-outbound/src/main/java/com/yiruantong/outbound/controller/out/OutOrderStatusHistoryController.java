package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.out.OutOrderStatusHistory;
import com.yiruantong.outbound.domain.out.vo.OutOrderStatusHistoryVo;
import com.yiruantong.outbound.domain.out.bo.OutOrderStatusHistoryBo;
import com.yiruantong.outbound.mapper.out.OutOrderStatusHistoryMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单状态历史记录
 *
 * @author YRT
 * @date 2023-11-10
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderStatusHistory")
public class OutOrderStatusHistoryController extends AbstractController<OutOrderStatusHistoryMapper, OutOrderStatusHistory, OutOrderStatusHistoryVo, OutOrderStatusHistoryBo> {
}

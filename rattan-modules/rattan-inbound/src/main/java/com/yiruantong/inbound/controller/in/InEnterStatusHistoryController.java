package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InEnterStatusHistory;
import com.yiruantong.inbound.domain.in.vo.InEnterStatusHistoryVo;
import com.yiruantong.inbound.domain.in.bo.InEnterStatusHistoryBo;
import com.yiruantong.inbound.mapper.in.InEnterStatusHistoryMapper;
import com.yiruantong.inbound.service.in.IInEnterStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入库记录轨迹
 *
 * @author YRT
 * @date 2023-11-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/enterStatusHistory")
public class InEnterStatusHistoryController extends AbstractController<InEnterStatusHistoryMapper, InEnterStatusHistory, InEnterStatusHistoryVo, InEnterStatusHistoryBo> {
}

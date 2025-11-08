package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InArrivalProcess;
import com.yiruantong.inbound.domain.in.vo.InArrivalProcessVo;
import com.yiruantong.inbound.domain.in.bo.InArrivalProcessBo;
import com.yiruantong.inbound.mapper.in.InArrivalProcessMapper;
import com.yiruantong.inbound.service.in.IInArrivalProcessService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 到货加工
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/arrivalProcess")
public class InArrivalProcessController extends AbstractController<InArrivalProcessMapper, InArrivalProcess, InArrivalProcessVo, InArrivalProcessBo> {
}

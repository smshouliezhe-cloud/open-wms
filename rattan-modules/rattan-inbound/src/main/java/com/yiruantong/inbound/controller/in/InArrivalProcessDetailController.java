package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.in.InArrivalProcessDetail;
import com.yiruantong.inbound.domain.in.vo.InArrivalProcessDetailVo;
import com.yiruantong.inbound.domain.in.bo.InArrivalProcessDetailBo;
import com.yiruantong.inbound.mapper.in.InArrivalProcessDetailMapper;
import com.yiruantong.inbound.service.in.IInArrivalProcessDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 到货加工明细
 *
 * @author YiRuanTong
 * @date 2023-10-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/arrivalProcessDetail")
public class InArrivalProcessDetailController extends AbstractController<InArrivalProcessDetailMapper, InArrivalProcessDetail, InArrivalProcessDetailVo, InArrivalProcessDetailBo> {
}

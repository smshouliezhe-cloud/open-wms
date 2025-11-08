package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InExchangeOuterDetail;
import com.yiruantong.inbound.domain.service.vo.InExchangeOuterDetailVo;
import com.yiruantong.inbound.domain.service.bo.InExchangeOuterDetailBo;
import com.yiruantong.inbound.mapper.service.InExchangeOuterDetailMapper;
import com.yiruantong.inbound.service.service.IInExchangeOuterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 换货单出库明细
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/exchangeOuterDetail")
public class InExchangeOuterDetailController extends AbstractController<InExchangeOuterDetailMapper, InExchangeOuterDetail, InExchangeOuterDetailVo, InExchangeOuterDetailBo> {
}

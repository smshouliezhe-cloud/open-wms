package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InExchange;
import com.yiruantong.inbound.domain.service.vo.InExchangeVo;
import com.yiruantong.inbound.domain.service.bo.InExchangeBo;
import com.yiruantong.inbound.mapper.service.InExchangeMapper;
import com.yiruantong.inbound.service.service.IInExchangeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 换货单
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/exchange")
public class InExchangeController extends AbstractController<InExchangeMapper, InExchange, InExchangeVo, InExchangeBo> {
}

package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.service.OutExchangeOuterDetail;
import com.yiruantong.outbound.domain.service.vo.OutExchangeOuterDetailVo;
import com.yiruantong.outbound.domain.service.bo.OutExchangeOuterDetailBo;
import com.yiruantong.outbound.mapper.service.OutExchangeOuterDetailMapper;
import com.yiruantong.outbound.service.service.IOutExchangeOuterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 换货管理Outer明细
 *
 * @author YiRuanTong
 * @date 2024-11-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/exchangeOuterDetail")
public class OutExchangeOuterDetailController extends AbstractController<OutExchangeOuterDetailMapper, OutExchangeOuterDetail, OutExchangeOuterDetailVo, OutExchangeOuterDetailBo> {
}

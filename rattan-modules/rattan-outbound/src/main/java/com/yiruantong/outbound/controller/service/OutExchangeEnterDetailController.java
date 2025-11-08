package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.service.OutExchangeEnterDetail;
import com.yiruantong.outbound.domain.service.vo.OutExchangeEnterDetailVo;
import com.yiruantong.outbound.domain.service.bo.OutExchangeEnterDetailBo;
import com.yiruantong.outbound.mapper.service.OutExchangeEnterDetailMapper;
import com.yiruantong.outbound.service.service.IOutExchangeEnterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 换货管理Enter明细
 *
 * @author YiRuanTong
 * @date 2024-11-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/exchangeEnterDetail")
public class OutExchangeEnterDetailController extends AbstractController<OutExchangeEnterDetailMapper, OutExchangeEnterDetail, OutExchangeEnterDetailVo, OutExchangeEnterDetailBo> {
}

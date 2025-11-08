package com.yiruantong.inbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.service.InExchangeEnterDetail;
import com.yiruantong.inbound.domain.service.vo.InExchangeEnterDetailVo;
import com.yiruantong.inbound.domain.service.bo.InExchangeEnterDetailBo;
import com.yiruantong.inbound.mapper.service.InExchangeEnterDetailMapper;
import com.yiruantong.inbound.service.service.IInExchangeEnterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 换货单入库明细
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/service/exchangeEnterDetail")
public class InExchangeEnterDetailController extends AbstractController<InExchangeEnterDetailMapper, InExchangeEnterDetail, InExchangeEnterDetailVo, InExchangeEnterDetailBo> {
}

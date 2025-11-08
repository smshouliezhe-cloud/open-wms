package com.yiruantong.outbound.controller.service;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.service.OutReturnDetail;
import com.yiruantong.outbound.domain.service.vo.OutReturnDetailVo;
import com.yiruantong.outbound.domain.service.bo.OutReturnDetailBo;
import com.yiruantong.outbound.mapper.service.OutReturnDetailMapper;
import com.yiruantong.outbound.service.service.IOutReturnDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 出库退货单明细
 *
 * @author YiRuanTong
 * @date 2023-10-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/service/returnDetail")
public class OutReturnDetailController extends AbstractController<OutReturnDetailMapper, OutReturnDetail, OutReturnDetailVo, OutReturnDetailBo> {
}

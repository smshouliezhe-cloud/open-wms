package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderMatchingDetail;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingDetailVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderMatchingDetailBo;
import com.yiruantong.outbound.mapper.operation.OutOrderMatchingDetailMapper;
import com.yiruantong.outbound.service.operation.IOutOrderMatchingDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单配货明细
 *
 * @author YRT
 * @date 2023-12-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderMatchingDetail")
public class OutOrderMatchingDetailController extends AbstractController<OutOrderMatchingDetailMapper, OutOrderMatchingDetail, OutOrderMatchingDetailVo, OutOrderMatchingDetailBo> {
}

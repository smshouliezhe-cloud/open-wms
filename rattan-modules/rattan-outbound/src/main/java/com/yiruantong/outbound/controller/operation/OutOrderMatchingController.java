package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderMatching;
import com.yiruantong.outbound.domain.operation.vo.OutOrderMatchingVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderMatchingBo;
import com.yiruantong.outbound.mapper.operation.OutOrderMatchingMapper;
import com.yiruantong.outbound.service.operation.IOutOrderMatchingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单配货
 *
 * @author YRT
 * @date 2023-12-09
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderMatching")
public class OutOrderMatchingController extends AbstractController<OutOrderMatchingMapper, OutOrderMatching, OutOrderMatchingVo, OutOrderMatchingBo> {
}

package com.yiruantong.outbound.controller.order;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.order.OutRetail;
import com.yiruantong.outbound.domain.order.vo.OutRetailVo;
import com.yiruantong.outbound.domain.order.bo.OutRetailBo;
import com.yiruantong.outbound.mapper.order.OutRetailMapper;
import com.yiruantong.outbound.service.order.IOutRetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订货单
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/order/retail")
public class OutRetailController extends AbstractController<OutRetailMapper, OutRetail, OutRetailVo, OutRetailBo> {
}

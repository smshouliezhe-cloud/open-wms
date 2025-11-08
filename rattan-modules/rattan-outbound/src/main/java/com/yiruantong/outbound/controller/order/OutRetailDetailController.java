package com.yiruantong.outbound.controller.order;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.order.OutRetailDetail;
import com.yiruantong.outbound.domain.order.vo.OutRetailDetailVo;
import com.yiruantong.outbound.domain.order.bo.OutRetailDetailBo;
import com.yiruantong.outbound.mapper.order.OutRetailDetailMapper;
import com.yiruantong.outbound.service.order.IOutRetailDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订货单明细
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/order/retailDetail")
public class OutRetailDetailController extends AbstractController<OutRetailDetailMapper, OutRetailDetail, OutRetailDetailVo, OutRetailDetailBo> {
}

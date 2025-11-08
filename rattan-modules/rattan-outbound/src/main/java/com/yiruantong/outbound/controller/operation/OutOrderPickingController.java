package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderPicking;
import com.yiruantong.outbound.domain.operation.vo.OutOrderPickingVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderPickingBo;
import com.yiruantong.outbound.mapper.operation.OutOrderPickingMapper;
import com.yiruantong.outbound.service.operation.IOutOrderPickingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单拣货查询
 *
 * @author YRT
 * @date 2023-12-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderPicking")
public class OutOrderPickingController extends AbstractController<OutOrderPickingMapper, OutOrderPicking, OutOrderPickingVo, OutOrderPickingBo> {
}

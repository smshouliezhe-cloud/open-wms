package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderSortPool;
import com.yiruantong.outbound.domain.operation.vo.OutOrderSortPoolVo;
import com.yiruantong.outbound.domain.operation.bo.OutOrderSortPoolBo;
import com.yiruantong.outbound.mapper.operation.OutOrderSortPoolMapper;
import com.yiruantong.outbound.service.operation.IOutOrderSortPoolService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分拣池
 *
 * @author YRT
 * @date 2024-05-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderSortPool")
public class OutOrderSortPoolController extends AbstractController<OutOrderSortPoolMapper, OutOrderSortPool, OutOrderSortPoolVo, OutOrderSortPoolBo> {
}

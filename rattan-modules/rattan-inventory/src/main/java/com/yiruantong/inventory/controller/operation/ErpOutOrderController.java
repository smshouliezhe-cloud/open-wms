package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.ErpOutOrder;
import com.yiruantong.inventory.domain.operation.vo.ErpOutOrderVo;
import com.yiruantong.inventory.domain.operation.bo.ErpOutOrderBo;
import com.yiruantong.inventory.mapper.operation.ErpOutOrderMapper;
import com.yiruantong.inventory.service.operation.IErpOutOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 其他出库单
 *
 * @author YRT
 * @date 2024-06-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/outOrder")
public class ErpOutOrderController extends AbstractController<ErpOutOrderMapper, ErpOutOrder, ErpOutOrderVo, ErpOutOrderBo> {
}

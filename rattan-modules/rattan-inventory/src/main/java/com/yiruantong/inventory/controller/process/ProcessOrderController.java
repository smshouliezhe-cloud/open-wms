package com.yiruantong.inventory.controller.process;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.process.ProcessOrder;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderVo;
import com.yiruantong.inventory.domain.process.bo.ProcessOrderBo;
import com.yiruantong.inventory.mapper.process.ProcessOrderMapper;
import com.yiruantong.inventory.service.process.IProcessOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加工列
 *
 * @author YRT
 * @date 2025-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/process/order")
public class ProcessOrderController extends AbstractController<ProcessOrderMapper, ProcessOrder, ProcessOrderVo, ProcessOrderBo> {
}

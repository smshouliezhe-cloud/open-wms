package com.yiruantong.inventory.controller.process;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.process.ProcessOrderDetail;
import com.yiruantong.inventory.domain.process.vo.ProcessOrderDetailVo;
import com.yiruantong.inventory.domain.process.bo.ProcessOrderDetailBo;
import com.yiruantong.inventory.mapper.process.ProcessOrderDetailMapper;
import com.yiruantong.inventory.service.process.IProcessOrderDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 加工列明细
 *
 * @author YRT
 * @date 2025-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/process/orderDetail")
public class ProcessOrderDetailController extends AbstractController<ProcessOrderDetailMapper, ProcessOrderDetail, ProcessOrderDetailVo, ProcessOrderDetailBo> {
}

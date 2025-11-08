package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateReturnCost;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnCostVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnCostBo;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnCostMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnCostService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器返厂费用明细
 *
 * @author YRT
 * @date 2024-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateReturnCost")
public class BasePlateReturnCostController extends AbstractController<BasePlateReturnCostMapper, BasePlateReturnCost, BasePlateReturnCostVo, BasePlateReturnCostBo> {
}

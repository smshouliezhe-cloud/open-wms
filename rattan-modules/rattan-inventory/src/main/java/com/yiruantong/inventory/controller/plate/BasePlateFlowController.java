package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateFlow;
import com.yiruantong.inventory.domain.plate.vo.BasePlateFlowVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateFlowBo;
import com.yiruantong.inventory.mapper.plate.BasePlateFlowMapper;
import com.yiruantong.inventory.service.plate.IBasePlateFlowService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器流水记录
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateFlow")
public class BasePlateFlowController extends AbstractController<BasePlateFlowMapper, BasePlateFlow, BasePlateFlowVo, BasePlateFlowBo> {
}

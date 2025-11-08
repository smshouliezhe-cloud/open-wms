package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateAdjustDetail;
import com.yiruantong.inventory.domain.plate.vo.BasePlateAdjustDetailVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateAdjustDetailBo;
import com.yiruantong.inventory.mapper.plate.BasePlateAdjustDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateAdjustDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器调整明细
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateAdjustDetail")
public class BasePlateAdjustDetailController extends AbstractController<BasePlateAdjustDetailMapper, BasePlateAdjustDetail, BasePlateAdjustDetailVo, BasePlateAdjustDetailBo> {
}

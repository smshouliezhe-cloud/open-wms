package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateReturnFactoryDetail;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnFactoryDetailVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnFactoryDetailBo;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnFactoryDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnFactoryDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器返厂单明细
 *
 * @author YRT
 * @date 2024-03-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateReturnFactoryDetail")
public class BasePlateReturnFactoryDetailController extends AbstractController<BasePlateReturnFactoryDetailMapper, BasePlateReturnFactoryDetail, BasePlateReturnFactoryDetailVo, BasePlateReturnFactoryDetailBo> {
}

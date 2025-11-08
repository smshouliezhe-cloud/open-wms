package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateInDetail;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInDetailBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInDetailVo;
import com.yiruantong.inventory.mapper.plate.BasePlateInDetailMapper;
import com.yiruantong.inventory.service.plate.IBasePlateInDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器归还明细
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateInDetail")
public class BasePlateInDetailController extends AbstractController<BasePlateInDetailMapper, BasePlateInDetail, BasePlateInDetailVo, BasePlateInDetailBo> {
}

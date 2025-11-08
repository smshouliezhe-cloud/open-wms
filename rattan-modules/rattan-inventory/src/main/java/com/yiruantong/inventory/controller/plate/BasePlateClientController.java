package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateClient;
import com.yiruantong.inventory.domain.plate.vo.BasePlateClientVo;
import com.yiruantong.inventory.domain.plate.bo.BasePlateClientBo;
import com.yiruantong.inventory.mapper.plate.BasePlateClientMapper;
import com.yiruantong.inventory.service.plate.IBasePlateClientService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户容器管理
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateClient")
public class BasePlateClientController extends AbstractController<BasePlateClientMapper, BasePlateClient, BasePlateClientVo, BasePlateClientBo> {
}

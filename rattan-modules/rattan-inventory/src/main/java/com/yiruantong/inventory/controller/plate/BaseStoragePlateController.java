package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BaseStoragePlate;
import com.yiruantong.inventory.domain.plate.vo.BaseStoragePlateVo;
import com.yiruantong.inventory.domain.plate.bo.BaseStoragePlateBo;
import com.yiruantong.inventory.mapper.plate.BaseStoragePlateMapper;
import com.yiruantong.inventory.service.plate.IBaseStoragePlateService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仓库容器查询
 *
 * @author YRT
 * @date 2024-03-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/storagePlate")
public class BaseStoragePlateController extends AbstractController<BaseStoragePlateMapper, BaseStoragePlate, BaseStoragePlateVo, BaseStoragePlateBo> {
}

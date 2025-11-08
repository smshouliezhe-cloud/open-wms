package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageSnAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageSnAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageSnAdjustDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageSnAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageSnAdjustDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SN调整明细
 *
 * @author YRT
 * @date 2024-09-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/snAdjustDetail")
public class StorageSnAdjustDetailController extends AbstractController<StorageSnAdjustDetailMapper, StorageSnAdjustDetail, StorageSnAdjustDetailVo, StorageSnAdjustDetailBo> {
}

package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageSnAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageSnAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StorageSnAdjustBo;
import com.yiruantong.inventory.mapper.operation.StorageSnAdjustMapper;
import com.yiruantong.inventory.service.operation.IStorageSnAdjustService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SN调整
 *
 * @author YRT
 * @date 2024-09-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/snAdjust")
public class StorageSnAdjustController extends AbstractController<StorageSnAdjustMapper, StorageSnAdjust, StorageSnAdjustVo, StorageSnAdjustBo> {
}

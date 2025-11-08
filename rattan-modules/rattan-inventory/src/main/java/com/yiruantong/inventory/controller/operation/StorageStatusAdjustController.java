package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageStatusAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StorageStatusAdjustBo;
import com.yiruantong.inventory.mapper.operation.StorageStatusAdjustMapper;
import com.yiruantong.inventory.service.operation.IStorageStatusAdjustService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 状态属性调整
 *
 * @author YRT
 * @date 2025-02-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/statusAdjust")
public class StorageStatusAdjustController extends AbstractController<StorageStatusAdjustMapper, StorageStatusAdjust, StorageStatusAdjustVo, StorageStatusAdjustBo> {
}

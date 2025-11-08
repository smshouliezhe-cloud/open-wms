package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageStatusAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageStatusAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageStatusAdjustDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageStatusAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageStatusAdjustDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 状态属性调整明细
 *
 * @author YRT
 * @date 2025-02-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/statusAdjustDetail")
public class StorageStatusAdjustDetailController extends AbstractController<StorageStatusAdjustDetailMapper, StorageStatusAdjustDetail, StorageStatusAdjustDetailVo, StorageStatusAdjustDetailBo> {
}

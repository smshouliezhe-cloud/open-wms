package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageValidAdjust;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StorageValidAdjustBo;
import com.yiruantong.inventory.mapper.operation.StorageValidAdjustMapper;
import com.yiruantong.inventory.service.operation.IStorageValidAdjustService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 效期信息调整
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/validAdjust")
public class StorageValidAdjustController extends AbstractController<StorageValidAdjustMapper, StorageValidAdjust, StorageValidAdjustVo, StorageValidAdjustBo> {
}

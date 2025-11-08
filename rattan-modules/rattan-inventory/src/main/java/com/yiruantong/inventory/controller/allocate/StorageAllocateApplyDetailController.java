package com.yiruantong.inventory.controller.allocate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyDetail;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyDetailVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyDetailBo;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateApplyDetailMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调拨申请单明细
 *
 * @author YRT
 * @date 2023-12-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/allocate/allocateApplyDetail")
public class StorageAllocateApplyDetailController extends AbstractController<StorageAllocateApplyDetailMapper, StorageAllocateApplyDetail, StorageAllocateApplyDetailVo, StorageAllocateApplyDetailBo> {
}

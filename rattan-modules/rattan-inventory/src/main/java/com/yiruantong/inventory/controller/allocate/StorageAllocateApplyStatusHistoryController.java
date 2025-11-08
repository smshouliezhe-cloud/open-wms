package com.yiruantong.inventory.controller.allocate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApplyStatusHistory;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyStatusHistoryVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyStatusHistoryBo;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateApplyStatusHistoryMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyStatusHistoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调拨申请单轨迹
 *
 * @author YRT
 * @date 2023-12-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/allocate/allocateApplyStatusHistory")
public class StorageAllocateApplyStatusHistoryController extends AbstractController<StorageAllocateApplyStatusHistoryMapper, StorageAllocateApplyStatusHistory, StorageAllocateApplyStatusHistoryVo, StorageAllocateApplyStatusHistoryBo> {
}

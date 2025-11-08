package com.yiruantong.inventory.controller.allocate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnterDetail;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterDetailVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateEnterDetailBo;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateEnterDetailMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateEnterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调拨入库单明细
 *
 * @author YRT
 * @date 2023-12-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/allocate/allocateEnterDetail")
public class StorageAllocateEnterDetailController extends AbstractController<StorageAllocateEnterDetailMapper, StorageAllocateEnterDetail, StorageAllocateEnterDetailVo, StorageAllocateEnterDetailBo> {
}

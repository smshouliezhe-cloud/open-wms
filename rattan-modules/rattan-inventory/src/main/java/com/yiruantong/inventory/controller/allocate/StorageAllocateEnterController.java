package com.yiruantong.inventory.controller.allocate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.allocate.StorageAllocateEnter;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateEnterVo;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateEnterBo;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateEnterMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateEnterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调拨入库单
 *
 * @author YRT
 * @date 2023-12-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/allocate/allocateEnter")
public class StorageAllocateEnterController extends AbstractController<StorageAllocateEnterMapper, StorageAllocateEnter, StorageAllocateEnterVo, StorageAllocateEnterBo> {
}

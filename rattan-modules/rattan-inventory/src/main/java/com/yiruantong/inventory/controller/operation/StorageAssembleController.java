package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageAssemble;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleBo;
import com.yiruantong.inventory.mapper.operation.StorageAssembleMapper;
import com.yiruantong.inventory.service.operation.IStorageAssembleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品拆装单
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/assemble")
public class StorageAssembleController extends AbstractController<StorageAssembleMapper, StorageAssemble, StorageAssembleVo, StorageAssembleBo> {
}

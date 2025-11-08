package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjust;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustVo;
import com.yiruantong.inventory.domain.operation.bo.StoragePurchasePriceAdjustBo;
import com.yiruantong.inventory.mapper.operation.StoragePurchasePriceAdjustMapper;
import com.yiruantong.inventory.service.operation.IStoragePurchasePriceAdjustService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存成本价调整
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/purchasePriceAdjust")
public class StoragePurchasePriceAdjustController extends AbstractController<StoragePurchasePriceAdjustMapper, StoragePurchasePriceAdjust, StoragePurchasePriceAdjustVo, StoragePurchasePriceAdjustBo> {
}

package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StoragePurchasePriceAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StoragePurchasePriceAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StoragePurchasePriceAdjustDetailBo;
import com.yiruantong.inventory.mapper.operation.StoragePurchasePriceAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStoragePurchasePriceAdjustDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存成本价调整明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/purchasePriceAdjustDetail")
public class StoragePurchasePriceAdjustDetailController extends AbstractController<StoragePurchasePriceAdjustDetailMapper, StoragePurchasePriceAdjustDetail, StoragePurchasePriceAdjustDetailVo, StoragePurchasePriceAdjustDetailBo> {
  private final IStoragePurchasePriceAdjustDetailService storagePurchasePriceAdjustDetailService;
  /**
   * 库存成本价调整明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectPurchasePriceAdjustDetailComposeList")
  public TableDataInfo<StoragePurchasePriceAdjustDetailComposeVo> selectPurchasePriceAdjustDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storagePurchasePriceAdjustDetailService.selectPurchasePriceAdjustDetailComposeList(pageQuery);
  }
}

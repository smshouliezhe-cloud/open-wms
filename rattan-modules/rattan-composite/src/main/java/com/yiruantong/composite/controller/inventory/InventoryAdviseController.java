package com.yiruantong.composite.controller.inventory;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.inventory.IInventoryAdviseService;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/inventoryAdvise")
class InventoryAdviseController extends BaseController {

  private final IInventoryAdviseService inventoryAdviseService;
  //#region 生成预到货单
  /**
   * 生成预到货单
   * @param dataList 数据集合
   * @return 提示信息
   */
  @PostMapping(value = "/toPurchaseOrder")
  public R<Void> toPurchaseOrder(@RequestBody List<CoreInventoryAdvise> dataList){
    return inventoryAdviseService.toPurchaseOrder(dataList);
  }
  //#endregion


  //#region 生成入库计划单
  /**
   * 生成入库计划单
   * @param dataList 数据集合
   * @return 提示信息
   */
  @PostMapping(value = "/toTmsQuotation")
  public R<Void> toTmsQuotation(@RequestBody List<CoreInventoryAdvise> dataList){
    return inventoryAdviseService.toTmsQuotation(dataList);
  }
  //#endregion

}

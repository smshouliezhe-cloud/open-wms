package com.yiruantong.composite.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.out.IOutOrderLockService;
import com.yiruantong.outbound.domain.out.bo.OrderDetailLackBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/out/outOrderLack")
class OutOrderLackController extends BaseController {

  private final IOutOrderLockService outOrderLockService;

  //#region 生成预到货单
  /**
   * 生成预到货单
   * @param dataList 数据集合
   * @return 提示信息
   */
  @PostMapping(value = "/toPurchaseOrder")
  public R<Void> toPurchaseOrder(@RequestBody List<OrderDetailLackBo> dataList){
    return outOrderLockService.toPurchaseOrder(dataList);
  }
  //#endregion

  //#region 生成入库计划单
  /**
   * 生成入库计划单
   * @param dataList 数据集合
   * @return 提示信息
   */
  @PostMapping(value = "/toTmsQuotation")
  public R<Void> toTmsQuotation(@RequestBody List<OrderDetailLackBo> dataList){
    return outOrderLockService.toTmsQuotation(dataList);
  }
  //#endregion

  //#region 出库单据撤回申请
  /**
   * 出库单据撤回申请
   * @param ids 订单集合
   */
  @PostMapping(value = "/cancelApply/{ids}")
  public R<Void> cancelApply(@PathVariable List<Long> ids) {
    return outOrderLockService.cancelApply(ids);
  }
  //#endregion

  //#region 出库单据撤回
  /**
   * 出库单据撤回
   * @param ids 订单集合
   */
  @PostMapping(value = "/orderCancel/{ids}")
  public R<Void> orderCancel(@PathVariable List<Long> ids) {
    return outOrderLockService.orderCancel(ids);
  }
  //#endregion



  //#region PDA分拣
  /**
   * PDA分拣
   * @param ids 订单集合
   */
  @PostMapping(value = "/pdaSorting/{ids}")
  public R<Void> pdaSorting(@PathVariable List<Long> ids) {
    return outOrderLockService.pdaSorting(ids);
  }
  //#endregion
}

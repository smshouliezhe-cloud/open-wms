package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import com.yiruantong.inbound.service.in.IInScanNoBillService;
import com.yiruantong.inbound.service.in.IInScanOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 入库扫描
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/inScanOrder")
class InScanOrderController extends BaseController {
  private final IInScanOrderService inScanOrderService;
  private final IInScanNoBillService inScanNoBillOrderService;

  /**
   * 常规扫描入库 - 获取扫描数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getInOrderData")
  public R<Map<String, Object>> getInOrderData(@RequestBody Map<String, Object> map) {
    return inScanOrderService.getInOrderData(map);
  }

  /**
   * 入库前校验：是否存在符合禁收日期
   *
   * @param inScanOrderBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/saveCheck")
  public R<Void> saveCheck(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanOrderService.saveCheck(inScanOrderBo);
  }

  /**
   * 常规扫描入库 - 预到货收货入库
   *
   * @param inScanOrderBo 常规扫描入库数据
   * @return 返回查询数据
   */
  @PostMapping("/normalScanSave")
  public R<Void> normalScanSave(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanOrderService.normalScanSave(inScanOrderBo);
  }

  /**
   * 常规扫描入库 - 预到货收货入库
   *
   * @param inScanOrderBo 常规扫描入库数据
   * @return 返回查询数据
   */
  @PostMapping("/normalScanSaveXG")
  public R<Void> normalScanSaveXG(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanOrderService.normalScanSaveXG(inScanOrderBo);
  }

  /**
   * 无单扫描确认入库
   *
   * @param inScanOrderBo 无单扫描数据
   * @return 返回查询数据
   */
  @PostMapping("/noBillEnterSave")
  public R<Void> noBillEnterSave(@RequestBody InScanOrderBo inScanOrderBo) {
    return inScanNoBillOrderService.noBillEnterSave(inScanOrderBo);
  }


  /**
   * 常规扫描入库 - 装箱收货入库 获取数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getOrderByProductModel")
  public R<List<Map<String, Object>>> getOrderByProductModel(@RequestBody Map<String, Object> map) {
    return inScanOrderService.getOrderByProductModel(map);
  }



  /**
   * 常规扫描入库 - 根据商品条码获取预到货单数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getPurchaseOrderData")
  public R<Map<String, Object>> getPurchaseOrderData(@RequestBody Map<String, Object> map) {
    return inScanOrderService.getPurchaseOrderData(map);
  }
}

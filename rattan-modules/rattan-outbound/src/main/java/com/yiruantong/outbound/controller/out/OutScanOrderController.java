package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.bo.OutOrderScanBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutScanNoBillService;
import com.yiruantong.outbound.service.out.IOutScanOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 出库订单
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/outScanOrder")
public class OutScanOrderController extends BaseController {
  private final IOutScanOrderService outScanOrderService;
  private final IOutScanNoBillService outScanNoBillService;

  /**
   * 常规扫描出库 - 获取扫描数据
   *
   * @param outOrderScanBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getOutOrderData")
  public R<Map<String, Object>> getOutOrderData(@RequestBody OutOrderScanBo outOrderScanBo) {
    return outScanOrderService.getOutOrderData(outOrderScanBo);
  }

  /**
   * 一键闪入 - 获取扫描数据
   *
   * @param outOrderScanBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getOutOrderDataFlashIn")
  public R<Map<String, Object>> getOutOrderDataFlashIn(@RequestBody OutOrderScanBo outOrderScanBo) {
    return outScanOrderService.getOutOrderDataFlashIn(outOrderScanBo);
  }

  /**
   * 常规扫描出库 - 保存扫描数据
   *
   * @param outScanMainBo 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/normalOutSave")
  public R<OutOrder> normalOutSave(@RequestBody OutScanMainBo outScanMainBo) {
    return outScanOrderService.normalOutSave(outScanMainBo);
  }

  /**
   * 无单扫描出库 - 保存扫描数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getNoBillProduct")
  public R<List<CoreInventoryComposeVo>> getNoBillProduct(@RequestBody Map<String, Object> map) {
    return outScanNoBillService.getNoBillProduct(map);
  }

  /**
   * 无单扫描出库 - 保存扫描数据
   *
   * @param outScanMainBo 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/noBillOutSave")
  public R<OutOrder> noBillOutSave(@RequestBody OutScanMainBo outScanMainBo) {
    return outScanNoBillService.noBillOutSave(outScanMainBo);
  }

  /**
   * 获取波次打包扫描数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getBatchPackageData")
  public R<Map<String, Object>> getBatchPackageData(@RequestBody Map<String, Object> map) {
    return outScanOrderService.getBatchPackageData(map);
  }

  /**
   * 保存出库单扫描数据
   *
   * @param outScanMainBo 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/saveBatchPackageData")
  public R<Void> saveBatchPackageData(@RequestBody OutScanMainBo outScanMainBo) {
    return outScanOrderService.saveBatchPackageData(outScanMainBo);
  }

  /**
   * 扫拍出库 - 获取扫描数据
   *
   * @param outOrderScanBo 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getOutOrderPlate")
  public R<Map<String, Object>> getOutOrderPlate(@RequestBody OutOrderScanBo outOrderScanBo) {
    return outScanOrderService.getOutOrderPlate(outOrderScanBo);
  }
}

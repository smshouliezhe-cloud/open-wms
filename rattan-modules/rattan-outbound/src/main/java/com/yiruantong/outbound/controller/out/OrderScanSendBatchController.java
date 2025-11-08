package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOrderScanSendBatchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 闪电发货效验
 *
 * @author h
 * @date 2023-12-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderScanSendBatch")
public class OrderScanSendBatchController extends BaseController {
  private final IOrderScanSendBatchService orderSendBatchService;


  /**
   * 闪电发货效验 - 获取波次下的订单数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getSendBatchData")
  public R<Map<String, Object>> getSendBatchData(@RequestBody Map<String, Object> map) {
    return orderSendBatchService.getSendBatchData(map);
  }


  /**
   * 闪电发货效验 - 保存闪电
   *
   * @param outScanMainBo 保存数据结构
   * @return 返回保存结果
   */
  @PostMapping("/saveSendBatchData")
  public R<Map<String, Object>> saveSendBatchData(@RequestBody OutScanMainBo outScanMainBo) {
    return orderSendBatchService.saveSendBatchData(outScanMainBo);
  }


  /**
   * 发货校验 - 获取订单数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getCheckExpressCode")
  public R<Map<String, Object>> getCheckExpressCode(@RequestBody Map<String, Object> map) {
    return orderSendBatchService.getCheckExpressCode(map);
  }

  /**
   * 发货校验 - 效验订单
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/orderSave")
  public R<Void> orderSave(@RequestBody Map<String, Object> map) {
    return orderSendBatchService.orderSave(map);
  }
}

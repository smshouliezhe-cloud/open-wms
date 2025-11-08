package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutScanPickingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 出库拣货下架
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/outScanPicking")
public class OutScanPickingController extends BaseController {
  private final IOutScanPickingService outScanPickingService;

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getPickingData")
  public R<Map<String, Object>> getPickingData(@RequestBody Map<String, Object> map) {
    return outScanPickingService.getPickingData(map);
  }

  /**
   * 摘果下架 - 获取波次明细数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getZgPickingData")
  public R<Map<String, Object>> getZgPickingData(@RequestBody Map<String, Object> map) {
    return outScanPickingService.getZgPickingData(map);
  }

  /**
   * 拣货人员领取任务
   *
   * @param map 领取任务
   * @return 返回保存结果
   */
  @PostMapping("/receiveTask")
  public R<Void> receiveTask(@RequestBody Map<String, Object> map) {
    return outScanPickingService.receiveTask(map);
  }

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param outScanMainBo 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/savePickingScan")
  public R<Map<String, Object>> savePickingScan(@RequestBody OutScanMainBo outScanMainBo) {
    return outScanPickingService.savePickingScan(outScanMainBo);
  }


  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getOffPositionShelveData")
  public R<List<CoreInventoryComposeVo>> getOffPositionShelveData(@RequestBody Map<String, Object> map) {
    return outScanPickingService.getOffPositionShelveData(map);
  }

  /**
   * 拣货下架 - 保存拣货下架回拣数据
   *
   * @param storageScanPositionTransferBo 保存参数
   * @return 返回保存结果
   */
  @PostMapping("/saveOffPositionShelveData")
  public R<Void> saveOffPositionShelveData(@RequestBody ScanPositionTransferBo storageScanPositionTransferBo) {
    return outScanPickingService.saveOffPositionShelveData(storageScanPositionTransferBo);
  }

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/checkSn")
  public R<Void> checkSn(@RequestBody Map<String, Object> map) {
    return outScanPickingService.checkSn(map);
  }
}

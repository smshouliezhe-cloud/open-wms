package com.yiruantong.composite.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.in.IInScanService;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/in/inScan")
class InScanController extends BaseController {

  private final IInScanService inScanService;

  /**
   * 码盘扫描入库 - 获取码盘数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getEnterStackingData")
  public R<List<Map<String, Object>>> getEnterStackingData(@RequestBody Map<String, Object> map) {
    return inScanService.getEnterStackingData(map);
  }

  /**
   * 码盘扫描入库 - 保存
   *
   * @param storageScanPositionTransferBo 参数
   * @return 返回查询数据
   */
  @PostMapping("/saveEnterStacking")
  public R<Void> saveEnterStacking(@RequestBody ScanPositionTransferBo storageScanPositionTransferBo) {
    return inScanService.saveEnterStacking(storageScanPositionTransferBo);
  }


}

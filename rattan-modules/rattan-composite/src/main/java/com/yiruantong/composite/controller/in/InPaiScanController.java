package com.yiruantong.composite.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.in.IInPaiScanService;
import com.yiruantong.inbound.domain.in.bo.InScanOrderBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/in/inPaiScan")
class InPaiScanController extends BaseController {

  private final IInPaiScanService inPaiScanService;

  /**
   * 按单码盘扫描保存前校验拍号是否存在
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/saveCheckPlateCode")
  public R<Void> saveCheckPlateCode(@RequestBody Map<String, Object> map) {
    return inPaiScanService.saveCheckPlateCode(map);
  }

  /**
   * 常规扫描入库 - 按单码盘扫描
   *
   * @param inScanOrderBo 常规扫描入库数据
   * @return 返回查询数据
   */
  @PostMapping("/scanPlateInSave")
  public R<Void> scanPlateInSave(@RequestBody InScanOrderBo inScanOrderBo) {
    return inPaiScanService.scanPlateInSave(inScanOrderBo);
  }

}

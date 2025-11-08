package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.outbound.service.out.IOutScanOrderPlatePickingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 出库按拍拣货下架
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/outScanOrderPlatePicking")
public class OutScanOrderPlatePickingController extends BaseController {
  private final IOutScanOrderPlatePickingService outScanOrderPlatePickingService;

  /**
   * 按拍下架 - 获取扫描数据
   *
   * @param map 查询条件
   * @return 返回查询数据
   */
  @PostMapping("/getInOrderData")
  public R<Map<String, Object>> getOutOrderWaveList(@RequestBody Map<String, Object> map) {
    return outScanOrderPlatePickingService.getOutOrderWaveList(map);
  }
}

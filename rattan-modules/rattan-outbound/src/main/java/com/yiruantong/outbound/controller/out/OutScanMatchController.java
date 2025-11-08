package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.service.out.IOutScanMatchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/outbound/out/outScanMatch")
public class OutScanMatchController extends BaseController {
  private final IOutScanMatchService outScanMatchService;

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getMatchData")
  public R<Map<String, Object>> getMatchData(@RequestBody Map<String, Object> map) {
    return outScanMatchService.getMatchData(map);
  }

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param outScanMainBo 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/saveMatchScan")
  public R<Void> saveMatchScan(@RequestBody OutScanMainBo outScanMainBo) {
    return outScanMatchService.saveMatchScan(outScanMainBo);
  }

  /**
   * 拣货下架 - 获取波次明细数据
   *
   * @param outScanMainBo 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/saveMatchBatchScan")
  public R<Void> saveMatchBatchScan(@RequestBody OutScanMainBo outScanMainBo) {
    return outScanMatchService.saveMatchBatchScan(outScanMainBo);
  }
}

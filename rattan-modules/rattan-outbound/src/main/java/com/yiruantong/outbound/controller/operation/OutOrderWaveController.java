package com.yiruantong.outbound.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.operation.OutOrderWave;
import com.yiruantong.outbound.domain.operation.bo.OutOrderWaveBo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveComposeVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveDetailPrintVo;
import com.yiruantong.outbound.domain.operation.vo.OutOrderWaveVo;
import com.yiruantong.outbound.domain.out.OutOrderDetail;
import com.yiruantong.outbound.mapper.operation.OutOrderWaveMapper;
import com.yiruantong.outbound.service.operation.IOutOrderWaveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 出库单波次
 *
 * @author YRT
 * @date 2023-11-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/operation/orderWave")
public class OutOrderWaveController extends AbstractController<OutOrderWaveMapper, OutOrderWave, OutOrderWaveVo, OutOrderWaveBo> {
  // 波次查询
  private final IOutOrderWaveService outOrderWaveService;

  /**
   * 取消主波次和对应的所有子波次的拣货人
   */
  @PostMapping(value = "/cancelNickName")
  public R<Map<String, Object>> cancelNickName(@RequestBody Map<String, Object> map) {
    return outOrderWaveService.cancelNickName(map);
  }

  @PostMapping(value = "/createOrderWave")
  public R<Void> createOrderWave(@RequestBody Map<String, Object> map) {
    return outOrderWaveService.createOrderWave(map);
  }

  @PostMapping(value = "/getOrderProductSpec")
  public R<List<OutOrderDetail>> getOrderProductSpec(@RequestBody Map<String, Object> map) {
    return R.ok(outOrderWaveService.getOrderProductSpec(map));
  }

  @PostMapping(value = "/onFrozenOrder")
  public R<Void> onFrozenOrder(@RequestBody Map<String, Object> map) {
    return outOrderWaveService.onFrozenOrder(map);
  }

  /*
   * 删除
   * */
  @RequestMapping("/deleteData")
  public R<Void> deleteData(@RequestBody Map<String, Object> map) {
    return outOrderWaveService.deleteData(map);
  }

  @PostMapping(value = "/selectOrderWave")
  public R<List<OutOrderWaveDetailPrintVo>> selectOrderWave(@RequestBody List<QueryBo> queryBoList) {
    return outOrderWaveService.selectOrderWave(queryBoList);
  }

  @PostMapping(value = "/updatePriority")
  public R<Void> updatePriority(@RequestBody Map<String, Object> map) {
    return outOrderWaveService.updatePriority(map);
  }

  @PostMapping(value = "/getSubWave/{orderWaveId}")
  public R<List<Map<String, Object>>> getSubWave(@PathVariable Long orderWaveId) {
    return outOrderWaveService.getSubWave(orderWaveId);
  }

  @PostMapping(value = "/cancelSubNickName/{subId}")
  public R<Void> cancelSubNickName(@PathVariable Long subId) {
    return outOrderWaveService.cancelSubNickName(subId);
  }

  @PostMapping(value = "/customPickBillDataList")
  public R<Map<String, Object>> customPickBillDataList(@RequestBody List<QueryBo> queryBoList) {
    return outOrderWaveService.customPickBillDataList(queryBoList);
  }

  /**
   * PDA 拣货任务列表自定义查询
   *
   * @param pageQuery
   * @return
   */
  @PostMapping(value = "/pdaPageList")
  public TableDataInfo<OutOrderWaveComposeVo> pdaPageList(@RequestBody PageQuery pageQuery) {
    return outOrderWaveService.pdaPageList(pageQuery);
  }
}

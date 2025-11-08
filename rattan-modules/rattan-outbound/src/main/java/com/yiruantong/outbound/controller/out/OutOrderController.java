package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.api.ApiOutOrderBo;
import com.yiruantong.outbound.domain.out.OutOrder;
import com.yiruantong.outbound.domain.out.OutSortingRule;
import com.yiruantong.outbound.domain.out.bo.OutOrderBo;
import com.yiruantong.outbound.domain.out.bo.OutScanMainBo;
import com.yiruantong.outbound.domain.out.vo.*;
import com.yiruantong.outbound.mapper.out.OutOrderMapper;
import com.yiruantong.outbound.service.out.IOutOrderApiService;
import com.yiruantong.outbound.service.out.IOutOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/outbound/out/order")
public class OutOrderController extends AbstractController<OutOrderMapper, OutOrder, OutOrderVo, OutOrderBo> {
  private final IOutOrderService outOrderService;
  private final IOutOrderApiService outOrderApiService;

  //#region 获取分拣列表
  @PostMapping(value = "/getSortingRule")
  public List<OutSortingRule> getSortingRule(@RequestBody Map<String, Object> map) {
    return outOrderService.getSortingRule(map);
  }

  /*
   * 出库单审核
   * */
  @PostMapping("/multiAuditing/{ids}")
  public R<Void> multiAuditing(@PathVariable List<Long> ids) {
    return outOrderService.multiAuditing(ids);
  }

  //#region 获取拣配单
  @PostMapping(value = "/getOutPickingList")
  public R<List<OutOrderDetailHolderComposeVO>> getOutPickingList(@RequestBody List<QueryBo> queryBoList) {
    return outOrderService.getOutPickingList(queryBoList);
  }
  //#endregion

  //#region 设置分拣规则
  @PostMapping(value = "/setSortingRule")
  public R<Void> setSortingRule(@RequestBody Map<String, Object> map) {
    return outOrderService.setSortingRule(map);
  }
  //#endregion

  //#region 关闭分拣规则
  @PostMapping(value = "/deleteSortingRule")
  public R<Void> deleteSortingRule(@RequestBody Map<String, Object> map) {
    return outOrderService.deleteSortingRule(map);
  }
  //#endregion


  /**
   * 自定义 缺货转预到货 查询 页面
   *
   * @param pageQuery 前台传入
   * @return 返回内容
   */
  @PostMapping("/orderDetailLackList")
  public TableDataInfo<OrderDetailLackVo> orderDetailLackList(@RequestBody PageQuery pageQuery) {
    return outOrderService.orderDetailLackList(pageQuery);
  }

  //#region 明细订单拆分（查询单据）
  @PostMapping(value = "/incorprationOrder")
  public R<Void> incorprationOrder(@RequestBody Map<String, Object> map) {
    return outOrderService.incorprationOrder(map);
  }
  //#endregion

  //#region 一键出库获取数据
  @PostMapping(value = "/getOrderOuterDetails")
  public R<List<OutOrderMainAndDetailVo>> getOrderOuterDetails(@RequestBody Map<String, Object> map) {
    return outOrderService.getOrderOuterDetails(map);
  }
  //#endregion


  //#region 一键出库
  @PostMapping(value = "/quickOut")
  public R<Void> quickOut(@RequestBody OutScanMainBo outScanMainBo) {
    var result = outOrderService.quickOut(outScanMainBo);
    return result;
  }
  //#endregion

  //#region 批量出库
  @PostMapping(value = "/batchOut")
  public R<Void> batchOut(@RequestBody Map<String, Object> map) {
    return outOrderService.batchOut(map);
  }
  //#endregion

  //#region 强制完成
  @PostMapping(value = "/forceFinish")
  public R<Void> forceFinish(@RequestBody Map<String, Object> map) {
    return outOrderService.forceFinish(map);
  }
  //#endregion

  //#region 获取打包明细的出库单
  @PostMapping(value = "/getOutIds")
  public R<Map<String, Object>> getOutIds(@RequestBody Map<String, Object> map) {
    return outOrderService.getOutIds(map);
  }
  //#endregion

  //#region 获取出库单和快递信息
  @PostMapping(value = "/getOutAndExpress")
  public R<Map<String, Object>> getOutAndExpress(@RequestBody Map<String, Object> map) {
    return outOrderService.getOutAndExpress(map);
  }
  //#endregion


  @PostMapping(value = "/selectOutPrint")
  public R<OutOrderPrintVo> selectOutPrint(@RequestBody List<QueryBo> queryBoList) {
    return outOrderService.selectOutPrint(queryBoList);
  }

  //#region 更新物流信息
  @PostMapping(value = "/updateLogistics")
  public R<Void> updateLogistics(@RequestBody Map<String, Object> map) {
    return outOrderService.updateLogistics(map);
  }
  //#endregion


  /**
   * PDA 出库首页统计信息
   */
  @PostMapping("/getStatisticData")
  public R<List<Map<String, Object>>> getStatisticData(@RequestBody Map<String, Object> maps) {
    return outOrderService.getStatisticData(maps);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiOutOrderBo bo) {
    return outOrderApiService.add(bo);
  }

  /**
   * 取消出库单
   */
  @Log(title = "取消出库单", businessType = BusinessType.CANCEL)
  @RepeatSubmit()
  @PostMapping("/cancel")
  public R<Map<String, Object>> cancel(@Validated(AddGroup.class) @RequestBody ApiOutOrderBo bo) {
    return outOrderApiService.cancel(bo);
  }

  /**
   * 更新出库单订单状态
   */
  @Log(title = "更新出库单订单状态", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping("/updateOrderStatus")
  public R<Map<String, Object>> updateOrderStatus(@RequestBody ApiOutOrderBo bo) {
    return outOrderApiService.updateOrderStatus(bo);
  }


  /**
   * 强制完成
   *
   * @param ids 参数
   */
  @PostMapping("/compulsoryAccomplish/{ids}")
  public R<Void> compulsoryAccomplish(@PathVariable List<Long> ids) {
    return outOrderService.compulsoryAccomplish(ids);
  }


  /**
   * 自定义打印页面查询数据
   *
   * @param queryBos 参数
   */
  @PostMapping("/mergePrintList")
  public R<Map<String, Object>> mergePrintList(@RequestBody List<QueryBo> queryBos) {
    return outOrderService.mergePrintList(queryBos);
  }


  //#region 一键出库 多单 （湘钢）
  @PostMapping(value = "/quickOutList")
  public R<Void> quickOutList(@RequestBody OutScanMainBo outScanMainBo) {
    var result = outOrderService.quickOutList(outScanMainBo);
    return result;
  }
  //#endregion
}

package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.dto.QueryBo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import com.yiruantong.inbound.domain.in.InOrder;
import com.yiruantong.inbound.domain.in.bo.InOrderBo;
import com.yiruantong.inbound.domain.in.vo.InOrderVo;
import com.yiruantong.inbound.mapper.in.InOrderMapper;
import com.yiruantong.inbound.service.in.IInOrderApiService;
import com.yiruantong.inbound.service.in.IInOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 预到货单
 *
 * @author YiRuanTong
 * @date 2023-10-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/order")
public class InOrderController extends AbstractController<InOrderMapper, InOrder, InOrderVo, InOrderBo> {
  private final IInOrderService inOrderService;
  private final IInOrderApiService inOrderApiService;

  /**
   * 批量审核
   *
   * @param ids 审核参数
   */
  @Log(title = "入库审核", businessType = BusinessType.AUDIT)
  @PostMapping("/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return inOrderService.multiAuditing(ids);
  }

  /**
   * 批量审核
   *
   * @param ids 审核参数
   */
  @Log(title = "入库审核", businessType = BusinessType.AUDIT)
  @PostMapping("/multiAuditing/{ids}")
  public R<Void> multiAuditing2(@PathVariable List<Long> ids) {
    return inOrderService.multiAuditing(ids);
  }

  /**
   * 确认为在途中
   *
   * @param ids 审核参数
   */
  @PostMapping("/onInTransit/{ids}")
  public R<Void> onInTransit(@PathVariable List<Long> ids) {
    return inOrderService.onInTransit(ids);
  }

  /**
   * 强制完成
   *
   * @param ids 参数
   */
  @PostMapping("/forceFinish/{ids}")
  public R<Void> forceFinish(@PathVariable List<Long> ids) {
    return inOrderService.forceFinish(ids);
  }

  /**
   * 拆分
   *
   * @param map 拆分
   */
  @PostMapping("/splitOrder")
  public R<Void> splitOrder(@RequestBody Map<String, Object> map) {
    return inOrderService.splitOrder(map);
  }

  /**
   * PDA页面筛选初始化数据
   */
  @GetMapping("/pdaWhereInit")
  public R<Map<String, Object>> pdaWhereInit() {
    return inOrderService.pdaWhereInit();
  }

  /**
   * PDA 入库首页统计信息
   */
  @PostMapping("/getStatisticData")
  public R<List<Map<String, Object>>> getStatisticData(@RequestBody Map<String, Object> maps) {
    return inOrderService.getStatisticData(maps);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiInOrderBo bo) {
    return inOrderApiService.add(bo);
  }


  /**
   * 修改数据
   */
  @Log(title = "修改数据", businessType = BusinessType.UPDATE)
  @RepeatSubmit()
  @PostMapping("/updateInOrder")
  public R<Map<String, Object>> updateInOrder(@Validated(AddGroup.class) @RequestBody ApiInOrderBo bo) {
    return inOrderApiService.updateInOrder(bo);
  }

  /**
   * 零库存出库
   */
  @PostMapping("/zeroOut/{ids}")
  public R<Void> zeroOut(@PathVariable List ids, BigDecimal unqualifiedQty) {
    return inOrderService.zeroOut(ids, unqualifiedQty);
  }


  /**
   * 强制收货
   *
   * @param ids 参数
   */
  @PostMapping("/compulsoryReceipt/{ids}")
  public R<Void> compulsoryReceipt(@PathVariable List<Long> ids) {
    return inOrderService.compulsoryReceipt(ids);
  }


  /**
   * 自定义打印明细托盘码
   *
   * @param queryBos 参数
   */
  @PostMapping("/printPlateCodeList")
  public R<Map<String, Object>> printPlateCodeList(@RequestBody List<QueryBo> queryBos) {
    return inOrderService.printPlateCodeList(queryBos);
  }

  /**
   * 送货完成归档
   *
   * @param ids 审核参数
   */
  @PostMapping("/deliveryComplet/{ids}")
  public R<Void> deliveryComplet(@PathVariable List<Long> ids) {
    return inOrderService.deliveryComplet(ids);
  }
}

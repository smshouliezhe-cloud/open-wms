package com.yiruantong.outbound.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.outbound.domain.api.ApiOutOrderPlanBo;
import com.yiruantong.outbound.domain.out.OutOrderPlan;
import com.yiruantong.outbound.domain.out.bo.OutOrderPlanBo;
import com.yiruantong.outbound.domain.out.vo.OutOrderPlanVo;
import com.yiruantong.outbound.mapper.out.OutOrderPlanMapper;
import com.yiruantong.outbound.service.out.IOutOrderPlanApiService;
import com.yiruantong.outbound.service.out.IOutOrderPlanService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 出库计划单
 *
 * @author YiRuanTong
 * @date 2023-10-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/outbound/out/orderPlan")
public class OutOrderPlanController extends AbstractController<OutOrderPlanMapper, OutOrderPlan, OutOrderPlanVo, OutOrderPlanBo> {
  private final IOutOrderPlanService OutOrderPlanService;
  private final IOutOrderPlanApiService outOrderPlanApiService;


  @PostMapping(value = "/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return OutOrderPlanService.multiAuditing(ids);
  }

  /**
   * 转出库单
   *
   * @param ids
   * @return
   */
  @PostMapping(value = "/toOutOrder/{ids}")

  public R<Void> toOutOrder(@PathVariable List<Long> ids) {
    return OutOrderPlanService.toOutOrder(ids);
  }


  /**
   * 确认重量
   *
   * @param map
   * @return
   */
  @PostMapping(value = "/confirmTheWeight")
  public R<Void> confirmTheWeight(@RequestBody Map<String, Object> map) {
    return OutOrderPlanService.confirmTheWeight(map);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiOutOrderPlanBo bo) {
    return outOrderPlanApiService.add(bo);
  }
}



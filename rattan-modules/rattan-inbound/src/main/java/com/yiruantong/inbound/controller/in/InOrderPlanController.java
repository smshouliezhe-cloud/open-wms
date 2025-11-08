package com.yiruantong.inbound.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inbound.domain.api.ApiInOrderPlanBo;
import com.yiruantong.inbound.domain.in.InOrderPlan;
import com.yiruantong.inbound.domain.in.bo.InOrderPlanBo;
import com.yiruantong.inbound.domain.in.vo.InOrderPlanVo;
import com.yiruantong.inbound.mapper.in.InOrderPlanMapper;
import com.yiruantong.inbound.service.in.IInOrderPlanApiService;
import com.yiruantong.inbound.service.in.IInOrderPlanService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 收货计划单
 *
 * @author BaiYu
 * @date 2023-10-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inbound/in/orderPlan")
public class InOrderPlanController extends AbstractController<InOrderPlanMapper, InOrderPlan, InOrderPlanVo, InOrderPlanBo> {
  private final IInOrderPlanService inOrderPlanService;
  private final IInOrderPlanApiService inOrderPlanApiService;

  /*
   * 入库计划单审核
   * */
  @PostMapping("/multiAuditing/{ids}")
  public R<Void> multiAuditing(@PathVariable List<Long> ids) {
    return inOrderPlanService.multiAuditing(ids);
  }

  /*
   * 入库计划转预到货
   * */
  @PostMapping("/toInOrder/{ids}")
  public R<Void> toInOrder(@PathVariable List<Long> ids) {
    return inOrderPlanService.toInOrder(ids);
  }

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiInOrderPlanBo bo) {
    return inOrderPlanApiService.add(bo);
  }
}

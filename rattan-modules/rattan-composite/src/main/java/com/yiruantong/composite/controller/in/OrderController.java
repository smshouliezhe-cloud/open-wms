package com.yiruantong.composite.controller.in;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.in.IOrderService;
import com.yiruantong.inbound.domain.api.ApiInOrderBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/in/order")
class OrderController extends BaseController {

  private final IOrderService orderService;
  /**
   * 根据ERP扣重退货自动扣减库存
   */
  @Log(title = "根据ERP扣重退货自动扣减库存", businessType = BusinessType.OTHER)
  @RepeatSubmit()
  @PostMapping("/inOrderChecking")
  public R<Map<String, Object>> inOrderChecking(@RequestBody ApiInOrderBo bo) {
    return orderService.inOrderChecking(bo);
  }
}

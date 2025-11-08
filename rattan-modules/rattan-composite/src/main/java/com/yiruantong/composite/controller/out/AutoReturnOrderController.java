package com.yiruantong.composite.controller.out;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.out.IAutoReturnOrderService;
import com.yiruantong.outbound.domain.api.ApiOutOrderBo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/out/autoReturnOrder")
class AutoReturnOrderController extends BaseController {
  private final IAutoReturnOrderService autoReturnOrderService;

  /**
   * 出库单退货-自动回退库存
   */
  @Log(title = "出库单退货", businessType = BusinessType.RETURN)
  @RepeatSubmit()
  @PostMapping("/toReturnOrder")
  public R<Map<String, Object>> toReturnOrder(@Validated(AddGroup.class) @RequestBody ApiOutOrderBo bo) {
    return autoReturnOrderService.toReturnOrder(bo);
  }
}

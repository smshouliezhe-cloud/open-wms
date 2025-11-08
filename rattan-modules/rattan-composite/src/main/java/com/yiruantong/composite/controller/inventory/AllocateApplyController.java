package com.yiruantong.composite.controller.inventory;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.inventory.IAllocateApplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/allocateApply")
class  AllocateApplyController extends BaseController {

  private final IAllocateApplyService allocateApplyService;
  /**
   * 转到出库单
   *
   * @param map 转到出库单
   * @return
   */
  @PostMapping("/toOutOrder")
  public R<Void> toOutOrder(@RequestBody Map<String, Object> map) {
    return allocateApplyService.toOutOrder(map);
  }

}

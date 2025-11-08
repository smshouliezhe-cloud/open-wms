package com.yiruantong.composite.controller.inventory;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.inventory.IAllocateApplyService;
import com.yiruantong.composite.service.inventory.IInventoryAttributeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/inventoryAttribute")
class InventoryAttributeController extends BaseController {

  private final IInventoryAttributeService inventoryAttributeService;
  @PostMapping("/attributeConvert")
  public R<Void> attributeConvert(@RequestBody Map<String, Object> map) {
    return inventoryAttributeService.attributeConvert(map);
  }

}

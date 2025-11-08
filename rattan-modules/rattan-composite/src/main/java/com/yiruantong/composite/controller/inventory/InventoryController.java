package com.yiruantong.composite.controller.inventory;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.BaseController;
import com.yiruantong.composite.service.inventory.IInventoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/composite/coreInventory")
class InventoryController extends BaseController {

  private final IInventoryService inventoryService;

  //#region 释放占位

  /**
   * 释放占位
   *
   * @param ids 删除ID
   * @return 提示信息
   */
  @DeleteMapping(value = "/releaseHolder/{ids}")
  @Log(title = "删除数据", businessType = BusinessType.DELETE)
  public R<Void> releaseHolder(@PathVariable Long[] ids) {
    return toAjax(inventoryService.deleteByIds(ids));
  }
  //#endregion


  /**
   * 获得角色权限右侧表格树列表
   *
   * @return 返回结果
   */
  @GetMapping("/getBadgeCount")
  public R<Map<String, Object>> getBadgeCount() {
    return inventoryService.getBadgeCount();
  }

  //#endregion


}

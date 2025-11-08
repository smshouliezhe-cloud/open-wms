package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageProfitLoss;
import com.yiruantong.inventory.domain.operation.bo.StorageProfitLossBo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossVo;
import com.yiruantong.inventory.mapper.operation.StorageProfitLossMapper;
import com.yiruantong.inventory.service.operation.IStorageProfitLossService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 盈亏单
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/profitLoss")
public class StorageProfitLossController extends AbstractController<StorageProfitLossMapper, StorageProfitLoss, StorageProfitLossVo, StorageProfitLossBo> {

  private final IStorageProfitLossService storageProfitLossService;

  //#region 生成盈亏单
  @PostMapping(value = "/createCheck")
  public R<Void> createCheck(@RequestBody Map<String, Object> map) {
    return storageProfitLossService.createCheck(map);
  }
  //#endregion

  //#region 调整库存
  @PostMapping(value = "/adjustInventory")
  public R<Void> adjustInventory(@RequestBody Map<String, Object> map) {
    return storageProfitLossService.adjustInventory(map);
  }
  //#endregion
}

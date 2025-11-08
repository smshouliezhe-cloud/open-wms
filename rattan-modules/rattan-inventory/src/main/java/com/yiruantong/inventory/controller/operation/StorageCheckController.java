package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageCheck;
import com.yiruantong.inventory.domain.operation.bo.StorageCheckBo;
import com.yiruantong.inventory.domain.operation.vo.CreateStorageCheckVo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckVo;
import com.yiruantong.inventory.mapper.operation.StorageCheckMapper;
import com.yiruantong.inventory.service.operation.IStorageCheckService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 盘点单
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/check")
public class StorageCheckController extends AbstractController<StorageCheckMapper, StorageCheck, StorageCheckVo, StorageCheckBo> {

  private final IStorageCheckService storageCheckService;

  //#region 创建盘点单
  @PostMapping(value = "/createOrderCheck")
  public R<Void> createOrderCheck(@RequestBody CreateStorageCheckVo createStorageCheckVo) {
    return storageCheckService.createOrderCheck(createStorageCheckVo);
  }
  //#endregion

  //#region 提交
  @PostMapping(value = "/subimtCheckBill")
  public R<Void> subimtCheckBill(@RequestBody Map<String, Object> map) {
    return storageCheckService.subimtCheckBill(map);
  }
  //#endregion

  //#region 生成盈亏单
  @PostMapping(value = "/createCheck")
  public R<Void> createCheck(@RequestBody Map<String, Object> map) {
    return storageCheckService.createCheck(map);
  }
  //#endregion

  //#region 调整库存
  @PostMapping(value = "/adjustInventory")
  public R<Void> adjustInventory(@RequestBody Map<String, Object> map) {
    return storageCheckService.adjustInventory(map);
  }
  //#endregion
}

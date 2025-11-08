package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageOuter;
import com.yiruantong.inventory.domain.operation.StorageOuterSortingRule;
import com.yiruantong.inventory.domain.operation.api.ApiStorageOuterBo;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterBo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterVo;
import com.yiruantong.inventory.mapper.operation.StorageOuterMapper;
import com.yiruantong.inventory.service.operation.IStorageOuterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 其他出库单
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/outer")
public class StorageOuterController extends AbstractController<StorageOuterMapper, StorageOuter, StorageOuterVo, StorageOuterBo> {
  private final IStorageOuterService storageOuterService;

  //#region 获取分拣列表
  @PostMapping(value = "/getSortingRule")
  public List<StorageOuterSortingRule> getSortingRule(@RequestBody Map<String, Object> map) {
    return storageOuterService.getSortingRule(map);
  }

  //#endregion
  //#region 设置分拣规则
  @PostMapping(value = "/setSortingRule")
  public R<Void> setSortingRule(@RequestBody Map<String, Object> map) {
    return storageOuterService.setSortingRule(map);
  }

  //#endregion
  //#region 关闭分拣规则
  @PostMapping(value = "/deleteSortingRule")
  public R<Void> deleteSortingRule(@RequestBody Map<String, Object> map) {
    return storageOuterService.deleteSortingRule(map);
  }
  //#endregion

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiStorageOuterBo bo) {
    return storageOuterService.add(bo);
  }

}

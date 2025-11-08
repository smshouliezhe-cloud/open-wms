package com.yiruantong.inventory.controller.allocate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.allocate.ApplySortingRule;
import com.yiruantong.inventory.domain.allocate.StorageAllocateApply;
import com.yiruantong.inventory.domain.allocate.bo.StorageAllocateApplyBo;
import com.yiruantong.inventory.domain.allocate.vo.StorageAllocateApplyVo;
import com.yiruantong.inventory.mapper.allocate.StorageAllocateApplyMapper;
import com.yiruantong.inventory.service.allocate.IStorageAllocateApplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 调拨申请单
 *
 * @author YRT
 * @date 2023-12-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/allocate/allocateApply")
public class StorageAllocateApplyController extends AbstractController<StorageAllocateApplyMapper, StorageAllocateApply, StorageAllocateApplyVo, StorageAllocateApplyBo> {
  private final IStorageAllocateApplyService storageAllocateApplyService;


  //#endregion
  //#region 设置分拣规则
  @PostMapping(value = "/setSortingRule")
  public R<Void> setSortingRule(@RequestBody Map<String, Object> map) {
    return storageAllocateApplyService.setSortingRule(map);
  }

  //#region 获取分拣列表
  @PostMapping(value = "/getSortingRule")
  public List<ApplySortingRule> getSortingRule(@RequestBody Map<String, Object> map) {
    return storageAllocateApplyService.getSortingRule(map);
  }

  //#endregion

  //#endregion
  //#region 关闭分拣规则
  @PostMapping(value = "/deleteSortingRule")
  public R<Void> deleteSortingRule(@RequestBody Map<String, Object> map) {
    return storageAllocateApplyService.deleteSortingRule(map);
  }
  //#endregion
}

package com.yiruantong.inventory.controller.replenishment;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.base.scan.ScanReplenishmentBo;
import com.yiruantong.inventory.domain.replenishment.StorageReplenishment;
import com.yiruantong.inventory.domain.replenishment.bo.StorageReplenishmentBo;
import com.yiruantong.inventory.domain.replenishment.vo.StorageReplenishmentVo;
import com.yiruantong.inventory.mapper.replenishment.StorageReplenishmentMapper;
import com.yiruantong.inventory.service.replenishment.IStorageReplenishmentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 补货单
 *
 * @author YRT
 * @date 2024-08-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/replenishment/replenishment")
public class StorageReplenishmentController extends AbstractController<StorageReplenishmentMapper, StorageReplenishment, StorageReplenishmentVo, StorageReplenishmentBo> {
  private final IStorageReplenishmentService storageReplenishmentService;

  /**
   * 审核
   *
   * @param ids 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping(value = "/multiAuditing")
  public R<Void> multiAuditing(@RequestBody List<Long> ids) {
    return storageReplenishmentService.multiAuditing(ids);
  }

  /**
   * 分拣
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping(value = "/sorting")
  public R<Void> sorting(@RequestBody Map<String, Object> map) {
    return storageReplenishmentService.sorting(map);
  }

  /**
   * 保存补货扫描
   *
   * @param storageScanReplenishmentBo 保存数据
   * @return R 返回保存结果
   */
  @PostMapping("/saveReplenishment")
  public R<Void> saveReplenishment(@RequestBody ScanReplenishmentBo storageScanReplenishmentBo) {
    return storageReplenishmentService.saveReplenishment(storageScanReplenishmentBo);
  }


}

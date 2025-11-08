package com.yiruantong.inventory.controller.operation;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.base.scan.ScanPositionTransferBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryComposeVo;
import com.yiruantong.inventory.domain.operation.StoragePositionTransfer;
import com.yiruantong.inventory.domain.operation.bo.StoragePositionTransferBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferVo;
import com.yiruantong.inventory.mapper.operation.StoragePositionTransferMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryService;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 货位转移
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/positionTransfer")
public class StoragePositionTransferController extends AbstractController<StoragePositionTransferMapper, StoragePositionTransfer, StoragePositionTransferVo, StoragePositionTransferBo> {
  private final ICoreInventoryService coreInventoryService;
  private final IStoragePositionTransferService storagePositionTransferService;

  /**
   * 根据原货位查询库存
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectValidInventoryListByPosition")
  public R<List<CoreInventoryComposeVo>> selectValidInventoryListByPosition(@RequestBody Map<String, Object> map) {
    String originalPositionName = Convert.toStr(map.get("originalPositionName"));
    Assert.isFalse(ObjectUtil.isEmpty(originalPositionName), "原货位不能为空");
    map.put("positionName", originalPositionName);
    List<CoreInventoryComposeVo> list = coreInventoryService.selectValidInventoryListByPosition(map);
    return R.ok(list);
  }

  /**
   * 保存货位转移
   *
   * @param storageScanPositionTransferBo 保存数据
   * @return R 返回保存结果
   */
  @PostMapping("/savePositionTransfer")
  public R<Void> savePositionTransfer(@RequestBody ScanPositionTransferBo storageScanPositionTransferBo) {
    return storagePositionTransferService.savePositionTransfer(storageScanPositionTransferBo);
  }
}

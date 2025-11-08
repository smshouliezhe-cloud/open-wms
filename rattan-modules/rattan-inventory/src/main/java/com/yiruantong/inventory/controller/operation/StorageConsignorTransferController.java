package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransfer;
import com.yiruantong.inventory.domain.operation.api.ApiStorageConsignorTransferBo;
import com.yiruantong.inventory.domain.operation.bo.StorageConsignorTransferBo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferVo;
import com.yiruantong.inventory.domain.operation.vo.TransferAppVo;
import com.yiruantong.inventory.mapper.operation.StorageConsignorTransferMapper;
import com.yiruantong.inventory.service.operation.IStorageConsignorTransferService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 货主过户
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/consignorTransfer")
public class StorageConsignorTransferController extends AbstractController<StorageConsignorTransferMapper, StorageConsignorTransfer, StorageConsignorTransferVo, StorageConsignorTransferBo> {
  private final IStorageConsignorTransferService storageConsignorTransferService;


  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiStorageConsignorTransferBo bo) {
    return storageConsignorTransferService.add(bo);
  }

  /**
   * 查询采购单和明细主从表关系
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/pageDetailList")
  public TableDataInfo<TransferAppVo> pageDetailList(@RequestBody PageQuery pageQuery) {
    return storageConsignorTransferService.pageDetailList(pageQuery);
  }
  /**
   * 根据采购单ID获取采购单和明细主从表关系
   *
   * @param consignorTransferId 货位过户ID
   * @return 返回查采购单数据
   */
  @PostMapping("/transferDetail/{consignorTransferId}")
  public R<TransferAppVo> transferDetail(@PathVariable Long consignorTransferId) {
    return storageConsignorTransferService.transferDetail(consignorTransferId);
  }
  /**
   * 根据采购单ID获取采购单和明细主从表关系
   *
   * @param transferAppVo 对象
   * @return 返回查采购单数据
   */
  @PostMapping("/transferAdd")
  public R<TransferAppVo> transferAdd(@RequestBody TransferAppVo transferAppVo) {
    return storageConsignorTransferService.transferAdd(transferAppVo);
  }
}

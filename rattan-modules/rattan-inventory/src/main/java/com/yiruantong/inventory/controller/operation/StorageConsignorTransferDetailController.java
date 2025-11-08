package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageConsignorTransferDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageConsignorTransferDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageConsignorTransferDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageConsignorTransferDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageConsignorTransferDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 货主过户
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/consignorTransferDetail")
public class StorageConsignorTransferDetailController extends AbstractController<StorageConsignorTransferDetailMapper, StorageConsignorTransferDetail, StorageConsignorTransferDetailVo, StorageConsignorTransferDetailBo> {
  private final IStorageConsignorTransferDetailService storageConsignorTransferDetailService;
  /**
   * 货主过户明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectConsignorTransferDetailComposeList")
  public TableDataInfo<StorageConsignorTransferDetailComposeVo> selectConsignorTransferDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageConsignorTransferDetailService.selectConsignorTransferDetailComposeList(pageQuery);
  }
}

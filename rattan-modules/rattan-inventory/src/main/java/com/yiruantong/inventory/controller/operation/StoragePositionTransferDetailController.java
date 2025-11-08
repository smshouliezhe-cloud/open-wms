package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StoragePositionTransferDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StoragePositionTransferDetailBo;
import com.yiruantong.inventory.mapper.operation.StoragePositionTransferDetailMapper;
import com.yiruantong.inventory.service.operation.IStoragePositionTransferDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 货位转移明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/positionTransferDetail")
public class StoragePositionTransferDetailController extends AbstractController<StoragePositionTransferDetailMapper, StoragePositionTransferDetail, StoragePositionTransferDetailVo, StoragePositionTransferDetailBo> {
  private final IStoragePositionTransferDetailService storagePositionTransferDetailService;
  /**
   * 货位转移明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectPositionTransferDetailComposeList")
  public TableDataInfo<StoragePositionTransferDetailComposeVo> selectPositionTransferDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storagePositionTransferDetailService.selectPositionTransferDetailComposeList(pageQuery);
  }
}

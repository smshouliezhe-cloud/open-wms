package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAdjustDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.mapper.operation.StorageAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageAdjustDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存调整单明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/adjustDetail")
public class StorageAdjustDetailController extends AbstractController<StorageAdjustDetailMapper, StorageAdjustDetail, StorageAdjustDetailVo, StorageAdjustDetailBo> {
  private final IStorageAdjustDetailService storageAdjustDetailService;
  /**
   * 库存调整单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectAdjustDetailComposeList")
  public TableDataInfo<StorageAdjustDetailComposeVo> selectAdjustDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageAdjustDetailService.selectAdjustDetailComposeList(pageQuery);
  }
}

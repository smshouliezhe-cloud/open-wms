package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageProfitLossDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageProfitLossDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageProfitLossDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageProfitLossDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageProfitLossDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 盈亏单明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/profitLossDetail")
public class StorageProfitLossDetailController extends AbstractController<StorageProfitLossDetailMapper, StorageProfitLossDetail, StorageProfitLossDetailVo, StorageProfitLossDetailBo> {
  private final IStorageProfitLossDetailService storageProfitLossDetailService;

  /**
   * 盈亏单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectProfitLossDetailComposeList")
  public TableDataInfo<StorageProfitLossDetailComposeVo> selectProfitLossDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageProfitLossDetailService.selectProfitLossDetailComposeList(pageQuery);
  }
}

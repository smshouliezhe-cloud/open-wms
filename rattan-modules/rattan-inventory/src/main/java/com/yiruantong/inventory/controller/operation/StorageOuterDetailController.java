package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageOuterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageOuterDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.mapper.operation.StorageOuterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageOuterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 其他出库明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/outerDetail")
public class StorageOuterDetailController extends AbstractController<StorageOuterDetailMapper, StorageOuterDetail, StorageOuterDetailVo, StorageOuterDetailBo> {
  private final IStorageOuterDetailService storageOuterDetailService;
  /**
   * 其他出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectStorageOuterDetailComposeList")
  public TableDataInfo<StorageOuterDetailComposeVo> selectStorageOuterDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageOuterDetailService.selectStorageOuterDetailComposeList(pageQuery);
  }
}

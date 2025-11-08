package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageValidAdjustDetail;
import com.yiruantong.inventory.domain.operation.vo.StoragePositionTransferDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageValidAdjustDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageValidAdjustDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageValidAdjustDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageValidAdjustDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 效期信息调整明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/validAdjustDetail")
public class StorageValidAdjustDetailController extends AbstractController<StorageValidAdjustDetailMapper, StorageValidAdjustDetail, StorageValidAdjustDetailVo, StorageValidAdjustDetailBo> {
  private final IStorageValidAdjustDetailService storageValidAdjustDetailService;
  /**
   * 效期信息调整明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectValidAdjustDetailComposeList")
  public TableDataInfo<StorageValidAdjustDetailComposeVo> selectValidAdjustDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageValidAdjustDetailService.selectValidAdjustDetailComposeList(pageQuery);
  }
}

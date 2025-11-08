package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageAssembleOuterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleOuterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleOuterDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageAssembleOuterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageAssembleEnterDetailService;
import com.yiruantong.inventory.service.operation.IStorageAssembleOuterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品拆装单出库明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/assembleOuterDetail")
public class StorageAssembleOuterDetailController extends AbstractController<StorageAssembleOuterDetailMapper, StorageAssembleOuterDetail, StorageAssembleOuterDetailVo, StorageAssembleOuterDetailBo> {
  private final IStorageAssembleOuterDetailService storageAssembleOuterDetailService;
  /**
   * 商品拆装单出库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectAssembleOuterDetailComposeList")
  public TableDataInfo<StorageAssembleOuterDetailComposeVo> selectAssembleOuterDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageAssembleOuterDetailService.selectAssembleOuterDetailComposeList(pageQuery);
  }
}

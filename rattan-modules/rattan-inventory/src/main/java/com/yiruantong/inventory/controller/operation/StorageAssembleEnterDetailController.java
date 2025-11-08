package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageAssembleEnterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageAdjustDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageAssembleEnterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageAssembleEnterDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageAssembleEnterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageAssembleEnterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品拆装单入库明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/assembleEnterDetail")
public class StorageAssembleEnterDetailController extends AbstractController<StorageAssembleEnterDetailMapper, StorageAssembleEnterDetail, StorageAssembleEnterDetailVo, StorageAssembleEnterDetailBo> {
  private final IStorageAssembleEnterDetailService storageAssembleEnterDetailService;
  /**
   * 商品拆装单入库明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectAssembleEnterDetailComposeList")
  public TableDataInfo<StorageAssembleEnterDetailComposeVo> selectAssembleEnterDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageAssembleEnterDetailService.selectAssembleEnterDetailComposeList(pageQuery);
  }
}

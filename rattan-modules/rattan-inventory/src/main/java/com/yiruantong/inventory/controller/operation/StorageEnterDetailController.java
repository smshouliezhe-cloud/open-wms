package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageEnterDetail;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterDetailVo;
import com.yiruantong.inventory.domain.operation.bo.StorageEnterDetailBo;
import com.yiruantong.inventory.mapper.operation.StorageEnterDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageEnterDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 其他入库单明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/enterDetail")
public class StorageEnterDetailController extends AbstractController<StorageEnterDetailMapper, StorageEnterDetail, StorageEnterDetailVo, StorageEnterDetailBo> {
  private final IStorageEnterDetailService storageEnterDetailService;
  /**
   * 其他入库单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectStorageEnterDetailComposeList")
  public TableDataInfo<StorageEnterDetailComposeVo> selectStorageEnterDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageEnterDetailService.selectStorageEnterDetailComposeList(pageQuery);
  }
}

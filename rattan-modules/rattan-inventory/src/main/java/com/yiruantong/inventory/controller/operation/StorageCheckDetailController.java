package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageCheckDetail;
import com.yiruantong.inventory.domain.operation.bo.StorageCheckDetailBo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailComposeVo;
import com.yiruantong.inventory.domain.operation.vo.StorageCheckDetailVo;
import com.yiruantong.inventory.mapper.operation.StorageCheckDetailMapper;
import com.yiruantong.inventory.service.operation.IStorageCheckDetailService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 盘点单明细
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/checkDetail")
public class StorageCheckDetailController extends AbstractController<StorageCheckDetailMapper, StorageCheckDetail, StorageCheckDetailVo, StorageCheckDetailBo> {
  private final IStorageCheckDetailService storageCheckDetailService;

  /**
   * 盘点单明细查询数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectStorageCheckDetailComposeList")
  public TableDataInfo<StorageCheckDetailComposeVo> selectStorageCheckDetailComposeList(@RequestBody PageQuery pageQuery) {
    return storageCheckDetailService.selectStorageCheckDetailComposeList(pageQuery);
  }

  /**
   * PDA盘点扫描 查询数据
   *
   * @param map 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/pdaCheckDetailList")
  public R<List<Map<String, Object>>> pdaCheckDetailList(@RequestBody Map<String, Object> map) {
    return storageCheckDetailService.pdaCheckDetailList(map);
  }

  /**
   * PDA盘点扫描 保存数据
   *
   * @param map 前段传入数据
   * @return 返回查询列表数据
   */
  @PostMapping("/pdaCheckSave")
  public R<Void> pdaCheckSave(@RequestBody Map<String, Object> map) {
    return storageCheckDetailService.pdaCheckSave(map);
  }
}

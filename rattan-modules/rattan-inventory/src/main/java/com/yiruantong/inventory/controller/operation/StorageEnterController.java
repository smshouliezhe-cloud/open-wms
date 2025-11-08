package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.core.validate.AddGroup;
import com.yiruantong.common.idempotent.annotation.RepeatSubmit;
import com.yiruantong.common.log.annotation.Log;
import com.yiruantong.common.log.enums.BusinessType;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.StorageEnter;
import com.yiruantong.inventory.domain.operation.api.ApiStorageEnterBo;
import com.yiruantong.inventory.domain.operation.bo.StorageEnterBo;
import com.yiruantong.inventory.domain.operation.vo.StorageEnterVo;
import com.yiruantong.inventory.mapper.operation.StorageEnterMapper;
import com.yiruantong.inventory.service.operation.IStorageEnterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 其他入库单
 *
 * @author YRT
 * @date 2023-10-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/enter")
public class StorageEnterController extends AbstractController<StorageEnterMapper, StorageEnter, StorageEnterVo, StorageEnterBo> {
  private final IStorageEnterService storageEnterService;

  /**
   * 新增数据
   */
  @Log(title = "新增数据", businessType = BusinessType.INSERT)
  @RepeatSubmit()
  @PostMapping("/add")
  public R<Map<String, Object>> add(@Validated(AddGroup.class) @RequestBody ApiStorageEnterBo bo) {
    return storageEnterService.add(bo);
  }
}

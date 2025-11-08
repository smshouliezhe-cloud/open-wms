package com.yiruantong.inventory.controller.operation;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.operation.CoreInventoryAdvise;
import com.yiruantong.inventory.domain.operation.bo.CoreInventoryAdviseBo;
import com.yiruantong.inventory.domain.operation.vo.CoreInventoryAdviseVo;
import com.yiruantong.inventory.mapper.operation.CoreInventoryAdviseMapper;
import com.yiruantong.inventory.service.operation.ICoreInventoryAdviseService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 建议采购转遇到货
 *
 * @author YRT
 * @date 2023-12-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/operation/inventoryAdvise")
public class CoreInventoryAdviseController extends AbstractController<CoreInventoryAdviseMapper, CoreInventoryAdvise, CoreInventoryAdviseVo, CoreInventoryAdviseBo> {
  private final ICoreInventoryAdviseService coreInventoryAdviseService;
  /**
   * 刷新数据
   *
   * @return R 返回结果
   */
  @GetMapping("/refreshData")
  public R<Void> refreshData() {
    return coreInventoryAdviseService.refreshData();
  }

}

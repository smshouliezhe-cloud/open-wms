package com.yiruantong.inventory.controller.core;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.core.CoreInventoryHolder;
import com.yiruantong.inventory.domain.core.bo.CoreInventoryHolderBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHolderComposeVo;
import com.yiruantong.inventory.domain.core.vo.CoreInventoryHolderVo;
import com.yiruantong.inventory.mapper.core.CoreInventoryHolderMapper;
import com.yiruantong.inventory.service.core.ICoreInventoryHolderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存占位查询(异常)
 *
 * @author YiRuanTong
 * @date 2023-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/core/inventoryHolder")
public class CoreInventoryHolderController extends AbstractController<CoreInventoryHolderMapper, CoreInventoryHolder, CoreInventoryHolderVo, CoreInventoryHolderBo> {
  private final ICoreInventoryHolderService coreInventoryHolderService;

  /**
   * 查询自定义库存占位数据
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectInventoryComposeHolderList")
  public TableDataInfo<CoreInventoryHolderComposeVo> selectInventoryComposeHolderList(@RequestBody PageQuery pageQuery) {
    return coreInventoryHolderService.selectInventoryComposeHolderList(pageQuery);
  }
}

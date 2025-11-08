package com.yiruantong.inventory.controller.core;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.core.CoreInventorySn;
import com.yiruantong.inventory.domain.core.bo.CoreInventorySnBo;
import com.yiruantong.inventory.domain.core.vo.CoreInventorySnVo;
import com.yiruantong.inventory.domain.core.vo.CoreSnComposeVo;
import com.yiruantong.inventory.mapper.core.CoreInventorySnMapper;
import com.yiruantong.inventory.service.core.ICoreInventorySnService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存明细SN
 *
 * @author YiRuanTong
 * @date 2023-10-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/core/inventorySn")
public class CoreInventorySnController extends AbstractController<CoreInventorySnMapper, CoreInventorySn, CoreInventorySnVo, CoreInventorySnBo> {
  final private ICoreInventorySnService coreInventorySnService;

  /**
   * 查询SN详细信息
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/selectSnComposeList")
  public TableDataInfo<CoreSnComposeVo> selectSnComposeList(@RequestBody PageQuery pageQuery) {
    return coreInventorySnService.selectSnComposeList(pageQuery);
  }


  /**
   * 查询SN详细信息
   *
   * @param inventoryId 库存ID
   * @return 返回查询列表数据
   */
  @PostMapping("/selectListByInventoryId/{inventoryId}")
  public R<List<CoreInventorySnVo>> selectListByInventoryId(@PathVariable long inventoryId) {
    return coreInventorySnService.selectListByInventoryId(inventoryId);
  }
}

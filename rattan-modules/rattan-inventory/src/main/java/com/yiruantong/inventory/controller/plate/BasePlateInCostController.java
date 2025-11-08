package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateInCost;
import com.yiruantong.inventory.domain.plate.bo.BasePlateInCostBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateInCostVo;
import com.yiruantong.inventory.mapper.plate.BasePlateInCostMapper;
import com.yiruantong.inventory.service.plate.IBasePlateInCostService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 容器归还费用明细
 *
 * @author YRT
 * @date 2024-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateInCost")
public class BasePlateInCostController extends AbstractController<BasePlateInCostMapper, BasePlateInCost, BasePlateInCostVo, BasePlateInCostBo> {
  private final IBasePlateInCostService basePlateInCostService;
  /**
   * 容易归还明细查询
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getBasePlateInDetailCompose")
  public TableDataInfo<BasePlateInCostComposeVo> getBasePlateInDetailCompose(@RequestBody PageQuery pageQuery) {
    return basePlateInCostService.getBasePlateInDetailCompose(pageQuery);
  }
}

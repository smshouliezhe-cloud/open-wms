package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.mybatis.core.page.PageQuery;
import com.yiruantong.common.mybatis.core.page.TableDataInfo;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateReturnOut;
import com.yiruantong.inventory.domain.plate.bo.BasePlateReturnOutBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnOutComposeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateReturnOutVo;
import com.yiruantong.inventory.mapper.plate.BasePlateReturnOutMapper;
import com.yiruantong.inventory.service.plate.IBasePlateReturnOutService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 返厂出库记录
 *
 * @author YRT
 * @date 2024-03-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateReturnOut")
public class BasePlateReturnOutController extends AbstractController<BasePlateReturnOutMapper, BasePlateReturnOut, BasePlateReturnOutVo, BasePlateReturnOutBo> {
  private final IBasePlateReturnOutService basePlateReturnOutService;
  /**
   * 容器返厂明细查询
   *
   * @param pageQuery 查询条件
   * @return 返回查询列表数据
   */
  @PostMapping("/getBasePlateReturnOutCompose")
  public TableDataInfo<BasePlateReturnOutComposeVo> getBasePlateReturnOutCompose(@RequestBody PageQuery pageQuery) {
    return basePlateReturnOutService.getBasePlateReturnOutCompose(pageQuery);
  }

}

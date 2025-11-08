package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateAdjust;
import com.yiruantong.inventory.domain.plate.bo.BasePlateAdjustBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateAdjustVo;
import com.yiruantong.inventory.mapper.plate.BasePlateAdjustMapper;
import com.yiruantong.inventory.service.plate.IBasePlateAdjustService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 容器调整主
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateAdjust")
public class BasePlateAdjustController extends AbstractController<BasePlateAdjustMapper, BasePlateAdjust, BasePlateAdjustVo, BasePlateAdjustBo> {

  private final IBasePlateAdjustService basePlateAdjustService;
  /**
   * 获取现借出数量
   *
   * @param map 查询条件
   * @return 返回保存结果
   */
  @PostMapping("/getNowOutQty")
  public BigDecimal getNowOutQty(@RequestBody Map<String, Object> map) {
    return basePlateAdjustService.getNowOutQty(map);
  }
}

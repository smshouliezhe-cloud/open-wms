package com.yiruantong.inventory.controller.plate;

import lombok.RequiredArgsConstructor;
import com.yiruantong.common.core.domain.R;
import com.yiruantong.common.web.core.AbstractController;
import com.yiruantong.inventory.domain.plate.BasePlateOut;
import com.yiruantong.inventory.domain.plate.bo.BasePlateOutBo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutDetailMergeVo;
import com.yiruantong.inventory.domain.plate.vo.BasePlateOutVo;
import com.yiruantong.inventory.mapper.plate.BasePlateOutMapper;
import com.yiruantong.inventory.service.plate.IBasePlateOutService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 容器借出主
 *
 * @author YRT
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory/plate/plateOut")
public class BasePlateOutController extends AbstractController<BasePlateOutMapper, BasePlateOut, BasePlateOutVo, BasePlateOutBo> {
  private final IBasePlateOutService basePlateService;
  /**
   * 容器归还根据客户名称获取源借出单号
   * @param map
   * @return
   */
  @PostMapping("/getSourceInfo")
  public R<List<Map<String, Object>>> getSourceInfo(@RequestBody Map<String, Object> map) {
    List<Map<String, Object>> basePlateList = basePlateService.getSourceInfo(map);
    return R.ok(basePlateList);
  }

  /**
   * 容器归还根据源借出单号查询对应的借出单信息
   * @param map
   * @return
   */
  @PostMapping("/getOrderInfo")
  public R <BasePlateOutDetailMergeVo> getOrderInfo(@RequestBody Map<String, Object> map) {
    return basePlateService.getOrderInfo(map);
  }
}
